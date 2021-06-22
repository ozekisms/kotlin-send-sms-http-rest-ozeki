package Ozeki.Libs.Rest.Results.MessageSend

import Ozeki.Libs.Rest.Message

class MessageSendResult(message: Message, status: DeliveryStatus, statusMessage: String) {
    var Message : Message = message
    var Status : DeliveryStatus = status
    var StatusMessage : String = statusMessage

    override fun toString(): String {
        return String.format("%s, %s", this.Status.toString(), this.Message.toString())
    }
}