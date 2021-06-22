package Ozeki.Libs.Rest.Results.MessageReceive

import Ozeki.Libs.Rest.Folder
import Ozeki.Libs.Rest.Message

class MessageReceiveResult(folder: Folder, limit: String, messages: ArrayList<Message>) {
    val Folder: Folder = folder
    val Limit: String = limit
    val Messages: ArrayList<Message> = messages

    override fun toString(): String {
        return String.format("Messages count: %d.", this.Messages.size)
    }
}