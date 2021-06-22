package send.sms

import Ozeki.Libs.Rest.Configuration
import Ozeki.Libs.Rest.Folder
import Ozeki.Libs.Rest.Message
import Ozeki.Libs.Rest.MessageApi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DeleteSms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_sms)

        val folderCategories:android.widget.Spinner = findViewById(R.id.folderCategories)
        val ID:android.widget.EditText = findViewById(R.id.inputId)
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
            if (ID.text.toString() != "") {

                val msg = Message()
                msg.ID = ID.text.toString()

                val folder: Folder

                when (folderCategories.selectedItem.toString()) {
                    "Outbox" -> folder = Folder.Outbox;
                    "Sent" -> folder = Folder.Sent;
                    "Not sent" -> folder = Folder.NotSent;
                    "Deleted" -> folder = Folder.Deleted;
                    else -> folder = Folder.Inbox
                }
                GlobalScope.launch(Dispatchers.IO) {
                    val response = api.Delete(folder, msg)
                    logBox.text = String.format("%s\n%s", logBox.text, response.toString())
                }
                ID.text.clear()
            }
        }
    }
}