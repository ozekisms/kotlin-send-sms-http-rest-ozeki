package send.sms

import Ozeki.Libs.Rest.Configuration
import Ozeki.Libs.Rest.Message
import Ozeki.Libs.Rest.MessageApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceiveSms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receive_sms)

        val listMessages:android.widget.ListView = findViewById(R.id.listMessages)
        val btnSendRequest:android.widget.Button = findViewById(R.id.btnSendRequest)

        val configuration = Configuration(
            username = "http_user",
            password = "qwe123",
            apiurl = "http://10.0.2.2:9509/api"
        )

        val messages_label : ArrayList<Message> = arrayListOf()

        val arrayAdapter = ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, messages_label)

        listMessages.adapter = arrayAdapter

        val api = MessageApi(configuration)

        btnSendRequest.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val result = api.DownloadIncomming()
                val messages = result.Messages
                for (index in 0 until messages.size) {
                    messages_label.add(messages.get(index))
                }
            }
            arrayAdapter.notifyDataSetChanged()
        }
    }
}