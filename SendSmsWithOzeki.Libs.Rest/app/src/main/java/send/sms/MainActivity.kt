package send.sms

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSendSms: android.widget.Button = findViewById(R.id.btnSendSms)
        val btnSendMultipleSms: android.widget.Button = findViewById(R.id.btnSendMultipleSms)
        val btnSendScheduledSms: android.widget.Button = findViewById(R.id.btnSendScheduledSms)
        val btnReceiveSms: android.widget.Button = findViewById(R.id.btnReceiveSms)
        val btnDeleteSms: android.widget.Button = findViewById(R.id.btnDeleteSms)

        btnSendSms.setOnClickListener {
            val intent = Intent(this, SendSms::class.java)
            startActivity(intent)
        }

        btnSendMultipleSms.setOnClickListener {
            val intent = Intent(this, SendMultipleSms::class.java)
            startActivity(intent)
        }

        btnSendScheduledSms.setOnClickListener {
            val intent = Intent(this, SendScheduledSms::class.java)
            startActivity(intent)
        }

        btnReceiveSms.setOnClickListener {
            val intent = Intent(this, ReceiveSms::class.java)
            startActivity(intent)
        }

        btnDeleteSms.setOnClickListener {
            val intent = Intent(this, DeleteSms::class.java)
            startActivity(intent)
        }
    }
}