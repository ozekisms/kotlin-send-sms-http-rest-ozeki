package Ozeki.Libs.Rest

enum class Folder {
    Inbox,
    Outbox,
    Sent,
    NotSent,
    Deleted,
    Null;

    override fun toString(): String {
        return when (this) {
            Inbox -> "inbox"
            Outbox -> "outbox"
            Sent -> "sent"
            NotSent -> "notsent"
            Deleted -> "deleted"
            Null -> ""
        }
    }

    fun parseFolder(folder: String) : Folder {
        return when(folder) {
            "inbox" -> Folder.Inbox
            "outbox" -> Folder.Outbox
            "sent" -> Folder.Sent
            "notsent" -> Folder.NotSent
            "deleted" -> Folder.Deleted
            else -> Folder.Null
        }
    }
}