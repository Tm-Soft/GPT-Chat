package kr.tmsoft.gptchat.repository

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.OpenAiDatabase
import kr.tmsoft.gptchat.data.room.dao.ChatRoomDao
import kr.tmsoft.gptchat.data.room.entity.ChatRoom
import kr.tmsoft.gptchat.util.DateConverter
import java.lang.Exception

class ChatRoomRepository(application: Application) {
    private val openAiDatabase = OpenAiDatabase.getInstance(application)!!
    private val chatRoomDao: ChatRoomDao = openAiDatabase.chatRoomDao()


    suspend fun getAllChatRoom(): Flow<List<ChatRoom>> = chatRoomDao.getAll()

    suspend fun insert(chatRoom: ChatRoom) {
        try { chatRoomDao.insert(chatRoom) }
        catch (_: Exception) { }
    }

    suspend fun updateContent(
        chatRoomSrl: Long,
        content: String
    ) {
        try {
            chatRoomDao.updateContent(
                chatRoomSrl = chatRoomSrl,
                content = content,
                date = DateConverter().getFullDate()
            )
        } catch (_: Exception) { }
    }

    suspend fun delete(chatRoom: ChatRoom) {
        try { chatRoomDao.delete(chatRoom) }
        catch (_: Exception) { }
    }
}