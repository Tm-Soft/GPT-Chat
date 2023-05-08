package kr.tmsoft.gptchat.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_content")
data class ChatContent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_content_srl")
    val chatContentSrl: Long = 0,

    @ColumnInfo(name = "chat_room_srl")
    val chatRoomSrl: Long,

    @ColumnInfo(name = "response_chat_content_srl")
    val responseChatContentSrl: Long?,

    @ColumnInfo(name = "role")
    val role: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "chat_id")
    val chatId: String?,

    @ColumnInfo(name = "total_token")
    val totalToken: Long?,

    @ColumnInfo(name = "last_update")
    val lastUpdate: Long,

    @ColumnInfo(name = "status")
    val status: String
)