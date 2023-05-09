package kr.tmsoft.gptchat.data.room.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_room")
data class ChatRoom(
    @PrimaryKey(autoGenerate = true)
    val chatRoomSrl: Long = 0,

    @ColumnInfo(name = "chat_title")
    val chatTitle: String,

    @ColumnInfo(name = "profile_uri")
    val profileUri: String?,

    @ColumnInfo(name = "last_view_date")
    val lastViewDate: Long?,

    @ColumnInfo(name = "last_update")
    val lastUpdate: Long
)