package Ozeki.Libs.Rest.Results.MessageManipulate.MessageDelete

import Ozeki.Libs.Rest.Folder

class MessageDeleteResult(folder: Folder, messageIdsRemoveSucceeded: ArrayList<String>, messageIdsRemoveFailed: ArrayList<String>) {
    val Folder: Folder = folder
    val MessageIdsRemoveSucceeded: ArrayList<String> = messageIdsRemoveSucceeded
    val MessageIdsRemoveFailed: ArrayList<String> = messageIdsRemoveFailed
    val SuccessCount: Int = messageIdsRemoveSucceeded.size
    val FailedCount: Int = messageIdsRemoveFailed.size
    val TotalCount: Int = this.SuccessCount + this.FailedCount

    override fun toString(): String {
        return String.format("Total: %d. Success: %d. Failed: %d.", this.TotalCount, this.SuccessCount, this.FailedCount)
    }

}