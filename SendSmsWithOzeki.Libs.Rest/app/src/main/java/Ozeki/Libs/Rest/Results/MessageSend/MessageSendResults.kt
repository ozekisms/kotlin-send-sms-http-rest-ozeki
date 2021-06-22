package Ozeki.Libs.Rest.Results.MessageSend

import Ozeki.Libs.Rest.Message

class MessageSendResults(totalCount: Int, successCount: Int, failedCount: Int, results: ArrayList<MessageSendResult>) {
    var TotalCount: Int = totalCount
    var SuccessCount: Int = successCount
    var FailedCount: Int = failedCount
    var Results: ArrayList<MessageSendResult> = results

    override fun toString(): String {
        return String.format("Total: %d. Success: %d. Failed: %d.", this.TotalCount, this.SuccessCount, this.FailedCount)
    }
}