package Ozeki.Libs.Rest

import Ozeki.Libs.Rest.Results.MessageManipulate.MessageDelete.MessageDeleteResult
import Ozeki.Libs.Rest.Results.MessageManipulate.MessageMark.MessageMarkResult
import Ozeki.Libs.Rest.Results.MessageReceive.MessageReceiveResult
import Ozeki.Libs.Rest.Results.MessageSend.DeliveryStatus
import Ozeki.Libs.Rest.Results.MessageSend.MessageSendResult
import Ozeki.Libs.Rest.Results.MessageSend.MessageSendResults
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MessageApi(configuration: Configuration) {
    val _configuration : Configuration = configuration

    @RequiresApi(Build.VERSION_CODES.O)
    fun createAuthHeader(username:String, password:String) : String {
        var usernamePassword = "%s:%s".format(username, password)
        return "Basic %s".format(Base64.getEncoder().encodeToString(usernamePassword.toByteArray()))
    }

    fun createRequestBody(message:Message) : String {
        val requestBody = JSONObject()
        val messages = JSONArray()
        messages.put(message.jsonVal())
        requestBody.put("messages", messages)
        return requestBody.toString()
    }

    fun createRequestBody(messages: ArrayList<Message>) : String {
        val requestBody = JSONObject()
        val msgs= JSONArray()
        for (i in 0 until messages.size) {
            msgs.put(messages.get(i).jsonVal())
        }
        requestBody.put("messages", msgs)
        return requestBody.toString()
    }

    fun createRequestBodyManipualte(folder:String, message:Message) : String {
        val requestBody = JSONObject()
        val messages = JSONArray()
        messages.put(message.ID)
        requestBody.put("folder", folder)
        requestBody.put("message_ids", messages)
        return requestBody.toString()
    }

    fun createRequestBodyManipualte(folder:String, messages:ArrayList<Message>) : String {
        val requestBody = JSONObject()
        val msgs = JSONArray()
        for (message in messages) {
            msgs.put(message.ID)
        }
        requestBody.put("folder", folder)
        requestBody.put("message_ids", msgs)
        return requestBody.toString()
    }

    fun createUriToSendMessage(url:String) : String {
        return "%s?action=sendmsg".format(url)
    }

    fun createUriToDeleteMessage(url:String) : String {
        return "%s?action=deletemsg".format(url)
    }

    fun createUriToMarkMessage(url:String) : String {
        return "%s?action=markmsg".format(url)
    }

    fun createUriToReceiveMessage(folder:Folder, url:String) : String {
        return "%s?action=receivemsg&folder=%s".format(url, folder.toString())
    }

    suspend fun DoRequestPost(url:String, authHeader:String, requestBody:String, manipulate:Boolean) : String {
        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaType()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", authHeader)
            .post(RequestBody.create(JSON, requestBody))
            .build()
        val response = client.newCall(request).execute()
        return response.body!!.string()
    }

    fun DoRequestGet(url:String, authHeader:String) : String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .header("Authorization", authHeader)
            .build()
        val response = client.newCall(request).execute()
        return response.body!!.string()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Send(message:Message) : MessageSendResult {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBody(message)
        return this.getResponse(this.DoRequestPost(this.createUriToSendMessage(this._configuration.ApiUrl), authHeader, requestBody, false)).Results[0]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Send(messages:ArrayList<Message>) : MessageSendResults {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBody(messages)
        return this.getResponse(this.DoRequestPost(this.createUriToSendMessage(this._configuration.ApiUrl), authHeader, requestBody, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Mark(message:Message) : Boolean {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBody(message)
        return this.getResponseManipulateMark(this.DoRequestPost(this.createUriToMarkMessage(this._configuration.ApiUrl), authHeader, requestBody, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Mark(messages: ArrayList<Message>) : MessageMarkResult {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBody(messages)
        return this.getResponseManipulateMark(this.DoRequestPost(this.createUriToMarkMessage(this._configuration.ApiUrl), authHeader, requestBody, false), messages)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Delete(folder:Folder, message:Message) : Boolean {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBodyManipualte(folder.toString(), message)
        return this.getResponseManipulateDelete(this.DoRequestPost(this.createUriToDeleteMessage(this._configuration.ApiUrl), authHeader, requestBody, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun Delete(folder:Folder, messages: ArrayList<Message>) : MessageDeleteResult {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        val requestBody = this.createRequestBodyManipualte(folder.toString(), messages)
        return this.getResponseManipulateDelete(this.DoRequestPost(this.createUriToDeleteMessage(this._configuration.ApiUrl), authHeader, requestBody, false), messages)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun DownloadIncomming() : MessageReceiveResult {
        val authHeader = this.createAuthHeader(this._configuration.Username, this._configuration.Password)
        return this.getMessages(this.DoRequestGet(this.createUriToReceiveMessage(Folder.Inbox, this._configuration.ApiUrl), authHeader))
    }

    fun getResponse(response:String) : MessageSendResults {
        val resp = JSONObject(response)
        if (resp.get("http_code") == 200) {
            val data = JSONObject(resp.get("data").toString())
            val totalCount = data.getInt("total_count")
            val successCount = data.getInt("success_count");
            val failedCount = data.getInt("failed_count");
            val results = ArrayList<MessageSendResult>();
            val messages = JSONArray(data.get("messages").toString());
            if (totalCount == 1) {
                val message = JSONObject(messages[0].toString())
                var status = DeliveryStatus.Success
                var statusMessage = ""
                if (message.getString("status") != "SUCCESS") {
                    status = DeliveryStatus.Failed;
                    statusMessage = message.getString("status");
                }
                results.add(MessageSendResult(Message().parseMessage(message), status, statusMessage))
                return MessageSendResults(totalCount, successCount, failedCount, results)
            } else if (data.get("success_count").toString().toInt() > 1) {
                for (i in 0 until messages.length()) {
                    val message = JSONObject(messages[i].toString())
                    var status = DeliveryStatus.Success
                    var statusMessage = ""
                    if (message.getString("status") != "SUCCESS") {
                        status = DeliveryStatus.Failed;
                        statusMessage = message.getString("status");
                    }
                    results.add(MessageSendResult(Message().parseMessage(message), status, statusMessage))
                }
                return MessageSendResults(totalCount, successCount, failedCount, results)
            }
        }
        return  MessageSendResults(0, 0, 0, ArrayList<MessageSendResult>())
    }

    fun getResponseManipulateDelete(response:String, messages: ArrayList<Message>) : MessageDeleteResult {
        val resp = JSONObject(response)
        if (resp.get("http_code") == 200) {
            val folder = resp.getJSONObject("data").getString("folder")
            val message_ids = resp.getJSONObject("data").getJSONArray("message_ids")
            val messages_success = ArrayList<String>();
            val messages_failed = ArrayList<String>();
            for (i in 0 until messages.size) {
                var success = false
                for (j in 0 until message_ids.length()) {
                    if (messages.get(i).ID.equals(message_ids.get(j))) {
                        success = true
                    }
                }
                if (success) {
                    messages_success.add(messages.get(i).ID)
                } else {
                    messages_failed.add(messages.get(i).ID)
                }
            }
            return MessageDeleteResult(Folder.Null.parseFolder(folder), messages_success, messages_failed)
        }
        return MessageDeleteResult(Folder.Null, ArrayList<String>(), ArrayList<String>())
    }

    fun getResponseManipulateDelete(response:String) : Boolean {
        val resp = JSONObject(response)
        if (resp.get("http_code") == 200) {
            val message_ids = resp.getJSONObject("data").getJSONArray("message_ids")
            if (message_ids.length() == 1) {
                return true
            }
        }
        return false
    }

    fun getResponseManipulateMark(response:String, messages: ArrayList<Message>) : MessageMarkResult {
        val resp = JSONObject(response)
        if (resp.get("http_code") == 200) {
            val folder = resp.getJSONObject("data").getString("folder")
            val message_ids = resp.getJSONObject("data").getJSONArray("message_ids")
            val messages_success = ArrayList<String>();
            val messages_failed = ArrayList<String>();
            for (i in 0 until messages.size) {
                var success = false
                for (j in 0 until message_ids.length()) {
                    if (messages.get(i).ID.equals(message_ids.get(j))) {
                        success = true
                    }
                }
                if (success) {
                    messages_success.add(messages.get(i).ID)
                } else {
                    messages_failed.add(messages.get(i).ID)
                }
            }
            return MessageMarkResult(Folder.Null.parseFolder(folder), messages_success, messages_failed)
        }
        return MessageMarkResult(Folder.Null, ArrayList<String>(), ArrayList<String>())
    }

    fun getResponseManipulateMark(response:String) : Boolean {
        val resp = JSONObject(response)
        if (resp.get("http_code") == 200) {
            val message_ids = resp.getJSONObject("data").getJSONArray("message_ids")
            if (message_ids.length() == 1) {
                return true
            }
        }
        return false
    }

    suspend fun getMessages(response:String) : MessageReceiveResult {
        val resp = JSONObject(response)
        val messages: ArrayList<Message> = arrayListOf()
        if (resp.get("http_code") == 200) {
            val folder: String = JSONObject(resp.get("data").toString()).getString("folder")
            val limit: String = JSONObject(resp.get("data").toString()).getString("limit")
            val msgs = JSONObject(resp.get("data").toString()).getJSONArray("data")
            for (i in 0 until msgs.length()) {
                val message = Message().parseMessage(msgs.getJSONObject(i))
                messages.add(message)
            }
            Delete(Folder.Inbox, messages)
            return MessageReceiveResult(Folder.Null.parseFolder(folder), limit, messages)
        }
        return MessageReceiveResult(Folder.Null, "", ArrayList<Message>())
    }
}