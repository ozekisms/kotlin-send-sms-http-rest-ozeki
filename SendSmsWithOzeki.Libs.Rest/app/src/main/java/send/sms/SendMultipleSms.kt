package send.sms

import Ozeki.Libs.Rest.Configuration
import Ozeki.Libs.Rest.Message
import Ozeki.Libs.Rest.MessageApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ArrayAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SendMultipleSms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_multiple_sms)

        val inputToAddress:android.widget.EditText = findViewById(R.id.inputToAddress)
        val inputMessage:android.widget.EditText = findViewById(R.id.inputMessage)
        val btnAddMessage:android.widget.Button = findViewById(R.id.btnAddMessage)
        val btnSendRequest:android.widget.Button = findViewById(R.id.btnSendRequest)
        val listOfMessages:android.widget.ListView = findViewById(R.id.listView)
        val logBox:android.widget.TextView = findViewById(R.id.logBox)
        logBox.movementMethod = ScrollingMovementMethod()

        val configuration = Configuration(
            username = "http_user",
            password = "qwe123",
            apiurl = "http://10.0.2.2:9509/api"
        )

        val api = MessageApi(configuration)

        val messages : ArrayList<Message> = arrayListOf()
        val messages_label : ArrayList<String> = arrayListOf()

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages_label)

        listOfMessages.adapter = arrayAdapter

        btnAddMessage.setOnClickListener {
            if (inputToAddress.text.toString() != "" && inputMessage.text.toString() != "" ) {
                val msg = Message()
                msg.ToAddress = inputToAddress.text.toString()
                msg.Text = inputMessage.text.toString()
                inputToAddress.text.clear()
                inputMessage.text.clear()
                messages_label.add(msg.toString())
                arrayAdapter.notifyDataSetChanged()
                messages.add(msg)
            } else {
                logBox.text = String.format("%s\nYou have to fill all the fields!", logBox.text)
            }
        }

        btnSendRequest.setOnClickListener {
            messages_label.clear()
            arrayAdapter.notifyDataSetChanged()
            GlobalScope.launch(Dispatchers.IO) {
                val response = api.Send(messages)
                messages.clear()
                logBox.text = String.format("%s\n%s", logBox.text, response.toString())
            }
        }
    }
}