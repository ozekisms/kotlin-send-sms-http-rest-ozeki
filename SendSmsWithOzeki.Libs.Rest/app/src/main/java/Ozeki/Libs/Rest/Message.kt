package Ozeki.Libs.Rest

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap
import org.json.*

class Message {
    var ID : String = UUID.randomUUID().toString()
    var FromConnection : String = ""
    var FromAddress : String = ""
    var FromStation : String = ""
    var ToConnection : String = ""
    var ToAddress : String = ""
    var ToStation : String = ""
    var Text : String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    var CreateDate : String = (LocalDateTime.now().toString()).split(".")[0]
    @RequiresApi(Build.VERSION_CODES.O)
    var ValidUntil : String = (LocalDateTime.now().plusDays(7).toString()).split(".")[0]
    @RequiresApi(Build.VERSION_CODES.O)
    var TimeToSend : String = (LocalDateTime.MIN.toString()).split(".")[0]
    var IsSubmitReportRequested : Boolean = true
    var IsDeliveryReportRequested : Boolean = true
    var IsViewReportRequested : Boolean = true
    var Tags = HashMap<String, String>()

    fun addTag(key:String, value:String) {
        this.Tags.put(key, value)
    }

    fun getTags() : JSONArray {
        val array = JSONArray()

        for ((key, value) in this.Tags) {
            val obj = JSONObject()
            obj.put(key, value)
            array.put(obj)
        }

        return array
    }

    override fun toString(): String {
        return String.format("->%s '%s'", this.ToAddress, this.Text);
    }

    fun jsonVal() : JSONObject {

        val jsonObject = JSONObject()

        if (this.ID != "") {
            jsonObject.put("message_id", this.ID)
        }
        if (this.FromConnection != "") {
            jsonObject.put("from_connection", this.FromConnection)
        }
        if (this.FromAddress != "") {
            jsonObject.put("from_address", this.FromAddress)
        }
        if (this.FromStation != "") {
            jsonObject.put("from_station", this.FromStation)
        }
        if (this.ToConnection != "") {
            jsonObject.put("to_connection", this.ToConnection)
        }
        if (this.ToAddress != "") {
            jsonObject.put("to_address", this.ToAddress)
        }
        if (this.ToStation != "") {
            jsonObject.put("to_station", this.ToStation)
        }
        if (this.Text != "") {
            jsonObject.put("text", this.Text)
        }
        if (this.CreateDate != "") {
            jsonObject.put("create_date", this.CreateDate)
        }
        if (this.ValidUntil != "") {
            jsonObject.put("valid_until", this.ValidUntil)
        }
        if (this.TimeToSend != "") {
            jsonObject.put("time_to_send", this.TimeToSend)
        }
        if (this.IsSubmitReportRequested) {
            jsonObject.put("submit_report_requested", true)
        } else {
            jsonObject.put("submit_report_requested", false)
        }
        if (this.IsDeliveryReportRequested) {
            jsonObject.put("delivery_report_requested", true)
        } else {
            jsonObject.put("delivery_report_requested", false)
        }
        if (this.IsViewReportRequested) {
            jsonObject.put("view_report_requested", true)
        } else {
            jsonObject.put("view_report_requested", false)
        }
        if (!this.Tags.isEmpty()) {
            jsonObject.put("tags", this.getTags())
        }

        return jsonObject
    }

    fun parseMessage(message: JSONObject) : Message {
        if (message.has("message_id")) {
            this.ID = message.getString("message_id")
        }
        if (message.has("from_connection")) {
            this.FromConnection = message.getString("from_connection")
        }
        if (message.has("from_address")) {
            this.FromAddress = message.getString("from_address")
        }
        if (message.has("from_station")) {
            this.FromStation = message.getString("from_station")
        }
        if (message.has("to_connection")) {
            this.ToConnection = message.getString("to_connection")
        }
        if (message.has("to_address")) {
            this.ToAddress = message.getString("to_address")
        }
        if (message.has("to_station")) {
            this.ToStation = message.getString("to_station")
        }
        if (message.has("text")) {
            this.Text = message.getString("text")
        }
        if (message.has("create_date")) {
            this.CreateDate = message.getString("create_date")
        }
        if (message.has("valid_until")) {
            this.ValidUntil = message.getString("valid_until")
        }
        if (message.has("time_to_send")) {
            this.TimeToSend = message.getString("time_to_send")
        }
        if (message.has("submit_report_requested")) {
            this.IsSubmitReportRequested = message.getBoolean("submit_report_requested")
        }
        if (message.has("delivery_report_requested")) {
            this.IsDeliveryReportRequested = message.getBoolean("delivery_report_requested")
        }
        if (message.has("view_report_requested")) {
            this.IsViewReportRequested = message.getBoolean("view_report_requested")
        }
        if (message.has("tags")) {
            val tags = message.getJSONArray("tags")
            for (i in 0 until tags.length()) {
                val tag = tags.getJSONObject(i)
                this.addTag(tag.getString("name"), tag.getString("value"))
            }
        }
        return this;
    }
}