package send.sms

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import Ozeki.Libs.Rest.Configuration
import Ozeki.Libs.Rest.Message
import Ozeki.Libs.Rest.MessageApi

class SendSms : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_sms)

        val btnSendRequest:android.widget.Button = findViewById(R.id.btnSendRequest)
        val inputToAddress:android.widget.EditText = findViewById(R.id.inputToAddress)
        val inputMessage:android.widget.EditText = findViewById(R.id.inputMessage)
        val logBox:android.widget.TextView = findViewById(R.id.logBox)
        logBox.movementMethod = ScrollingMovementMethod()

        val configuration = Configuration(
            username = "http_user",
            password = "qwe123",
            apiurl = "http://10.0.2.2:9509/api"
        )

        val api = MessageApi(configuration)

        btnSendRequest.setOnClickListener {
            if (inputToAddress.text.toString() != "" && inputMessage.text.toString() != "") {
                GlobalScope.launch(Dispatchers.IO) {
                    val msg = Message()
                    msg.ToAddress = inputToAddress.text.toString()
                    msg.Text = inputMessage.text.toString()
                    inputToAddress.text.clear()
                    inputMessage.text.clear()
                    val response = api.Send(msg)
                    logBox.text = String.format("%s\n%s", logBox.text, response.toString())
                }
            }
        }
    }
}