package kr.tmsoft.gptchat.repository

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.OpenAiDatabase
import kr.tmsoft.gptchat.data.room.dao.ChatContentDao
import kr.tmsoft.gptchat.data.room.dao.ChatRoomDao
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.data.room.entity.ChatRoom
import kr.tmsoft.gptchat.util.DateConverter
import java.lang.Exception

class ChatRoomRepository(application: Application) {
    private val openAiDatabase = OpenAiDatabase.getInstance(application)!!
    private val chatRoomDao: ChatRoomDao = openAiDatabase.chatRoomDao()
    private val chatContentDao: ChatContentDao = openAiDatabase.chatContentDao()

    fun getAllChatRoom(): Flow<List<ChatRoom>> = chatRoomDao.getAll()

    fun getChatLastContent(chatRoomSrl: Long) = chatContentDao.getAssistantLastContent(chatRoomSrl)

    fun getQuestionContent(contentSrl: Long) = chatContentDao.getQuestionContent(contentSrl)

    fun insertChatRoom(chatRoom: ChatRoom) = chatRoomDao.insert(chatRoom)

    fun insertChatContent(chatContent: ChatContent) {
        try { chatContentDao.insert(chatContent) }
        catch (_: Exception) { }
    }

    fun updateContent(
        chatRoomSrl: Long,
        content: String
    ) {
        try {
            chatRoomDao.updateLastDate(
                chatRoomSrl = chatRoomSrl,
                date = DateConverter().getFullDate()
            )
        } catch (_: Exception) { }
    }

    fun delete(chatRoom: ChatRoom) {
        try { chatRoomDao.delete(chatRoom) }
        catch (_: Exception) { }
    }
}