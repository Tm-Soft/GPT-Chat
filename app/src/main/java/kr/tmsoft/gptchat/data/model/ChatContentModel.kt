package kr.tmsoft.gptchat.data.model

import android.net.Uri

data class ChatContentModel(
    val chatContentSrl: Long,
    val role: String,
    val content: String,
    val profileUri: Uri?
)