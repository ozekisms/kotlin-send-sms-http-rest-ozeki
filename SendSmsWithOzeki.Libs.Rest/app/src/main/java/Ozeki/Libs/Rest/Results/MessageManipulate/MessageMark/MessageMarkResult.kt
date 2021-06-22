package Ozeki.Libs.Rest.Results.MessageManipulate.MessageMark

import Ozeki.Libs.Rest.Folder

class MessageMarkResult(folder: Folder, messageIdsMarkSucceeded: ArrayList<String>, messageIdsMarkFailed: ArrayList<String>) {
    val Folder: Folder = folder
    val MessageIdsMarkSucceeded: ArrayList<String> = messageIdsMarkSucceeded
    val MessageIdsMarkFailed: ArrayList<String> = messageIdsMarkFailed
    val SuccessCount: Int = messageIdsMarkSucceeded.size
    val FailedCount: Int = messageIdsMarkFailed.size
    val TotalCount: Int = this.SuccessCount + this.FailedCount

    override fun toString(): String {
        return String.format("Total: %d. Success: %d. Failed: %d.", this.TotalCount, this.SuccessCount, this.FailedCount)
    }

}