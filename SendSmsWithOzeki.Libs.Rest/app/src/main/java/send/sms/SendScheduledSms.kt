package send.sms

import Ozeki.Libs.Rest.Configuration
import Ozeki.Libs.Rest.Message
import Ozeki.Libs.Rest.MessageApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SendScheduledSms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_scheduled_sms)

        val inputToAddress:android.widget.EditText = findViewById(R.id.inputToAddress)
        val inputMessage:android.widget.EditText = findViewById(R.id.inputMessage)
        val date:android.widget.EditText = findViewById(R.id.inputTimeToSendDate)
        val time:android.widget.EditText = findViewById(R.id.inputTimeToSendTime)
        val btnSendRequest:android.widget.Button = findViewById(R.id.btnSendRequest)
        val logBox:android.widget.TextView = findViewById(R.id.logBox)
        logBox.movementMethod = ScrollingMovementMethod()


        val configuration = Configuration(
            username = "http_user",
            password = "qwe123",
            apiurl = "http://10.0.2.2:9509/api"
        )

        val api = MessageApi(configuration)

        btnSendRequest.setOnClickListener {
            if (inputToAddress.text.toString() != "" && inputMessage.text.toString() != ""
                && date.text.toString() != "" && time.text.toString() != "") {
                val datetime = String.format("%s %s", date.text.toString(), time.text.toString())
                val msg = Message()
                msg.ToAddress = inputToAddress.text.toString()
                msg.Text = inputMessage.text.toString()
                msg.TimeToSend = datetime
                GlobalScope.launch(Dispatchers.IO) {
                    val response = api.Send(msg)
                    logBox.text = String.format("%s\n%s", logBox.text, response.toString())
                }
                inputToAddress.text.clear()
                inputMessage.text.clear()
                date.text.clear()
                time.text.clear()
            }
        }
    }
}