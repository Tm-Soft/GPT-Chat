package kr.tmsoft.gptchat.data.model

data class ChatRoomModel(
    val chatRoomSrl: Long,
    val title: String,
    val question: String?,
    val content: String,
    val profileUri: String?,
    val lastViewDate: Long?,
    val lastUpdate: Long
)