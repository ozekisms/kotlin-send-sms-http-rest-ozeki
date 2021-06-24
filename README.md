# Kotlin sms library to send sms with http/rest/json

This kotlin sms library enables you to **send sms** from kotlin with http requests. 
You may also use it to **receive sms** from kotlin with http downloads. The library
uses HTTP Post requests and JSON encoded content to send the text
messages to the mobile network1. It connects to the HTTP SMS API of 
[Ozeki SMS gateway](https://ozeki-sms-gateway.com). This repository is better
for implementing SMS solutions then other alternatives, because it allows
you to use the same code to send SMS through an Android mobile, through
a high performance IP SMS connection or a GSM modem or modem pool. This
library gives you SMS service provider independence.

## What is Ozeki SMS Gateway 

Ozeki SMS Gateway is a high-performance SMS Gateway that could send up to 1000 SMS per second, while being reliable and easy to use. It runs on an environment that you control, so it means maximum security for your data a contact list. You can download and install the gateway on your Windows, Linux, or Android device. It provides an HTTP SMS API, so you can connect it remotely or locally. The Ozeki SMS Gateway provides full independence from service providers, so it could be used in any country and could connect to mobile networks directly and through wireless connection.

Download: [Ozeki SMS Gateway download page](https://ozeki-sms-gateway.com/p_727-download-sms-gateway.html)

Tutorial: [Kotlin send sms sample and tutorial](https://ozeki-sms-gateway.com/p_843-kotlin-send-sms-with-the-http-rest-api-code-sample.html)

## How to send sms from kotlin: 

**To send sms from kotlin**
1. [Download Ozeki SMS Gateway](https://ozeki-sms-gateway.com/p_727-download-sms-gateway.html)
2. [Connect Ozeki SMS Gateway to the mobile network](https://ozeki-sms-gateway.com/p_70-mobile-network-connections.html)
3. [Create an HTTP SMS API user](https://ozeki-sms-gateway.com/p_2102-create-an-http-sms-api-user-account.html)
4. Open Android Studio
5. Create the SMS by creating a new Message object
6. Create an api to send your message
7. Use the Send method to send your message
8. Read the response message on the console
9. Check the logs in the SMS gateway

## How to use the code

To use the code you need to import the Ozeki.Libs.Rest sms library. This
sms library is also included in this repositry with it's full source code.
After the library is imported with the using statiment, you need to define
the username, password and the api url. You can create the username and 
password when you install an HTTP API user in your Ozeki SMS Gateway system.

The URL is the default http api URL to connect to your SMS gateway. If you
run the SMS gateway on the same computer where your kotlin code is runing, you
can use 127.0.0.1 as the ip address. You need to change this if you install
the sms gateway on a different computer (or mobile phone).


```
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
 
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
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
  
```

## Manual / API reference

To get a better understanding of the above **SMS code sample**, feel free to visit the 

Link: [How to send sms from kotlin](https://ozeki-sms-gateway.com/p_843-kotlin-send-sms-with-the-http-rest-api-code-sample.html)


## How to send sms through your Android mobile phone

If you wish to [send SMS through your Android mobile phone from kotlin](https://android-sms-gateway.com/), 
you need to [install Ozeki SMS Gateway on your Android](https://ozeki-sms-gateway.com/p_2847-how-to-install-ozeki-sms-gateway-on-android.html) 
mobile phone. It is recommended to use an Android mobile phone with a minimum of 
4GB RAM and a quad core CPU. Most devices today meet these specs. The advantage
of using your mobile, is that it is quick to setup and it often allows you
to [send sms free of charge](https://android-sms-gateway.com/p_246-how-to-send-sms-free-of-charge.html).

[Android SMS Gateway](https://android-sms-gateway.com)

## Get started now

Don't hesitate, use this repository and start sending SMS now!
