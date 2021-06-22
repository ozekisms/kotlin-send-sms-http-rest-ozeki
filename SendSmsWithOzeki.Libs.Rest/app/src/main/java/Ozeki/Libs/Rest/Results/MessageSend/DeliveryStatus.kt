package Ozeki.Libs.Rest.Results.MessageSend

public enum class DeliveryStatus {
    Success,
    Failed;

    override fun toString(): String {
        return when (this) {
            Failed -> "Failed"
            Success -> "Success"
        }
    }
}