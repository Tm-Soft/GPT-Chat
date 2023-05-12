package kr.tmsoft.gptchat.repository

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.OpenAiDatabase
import kr.tmsoft.gptchat.data.room.dao.ChatContentDao
import kr.tmsoft.gptchat.data.room.entity.ChatContent

class ChatContentLocalRepository(application: Application) {
    private val openAiDatabase = OpenAiDatabase.getInstance(application)!!
    private val chatContentDao: ChatContentDao = openAiDatabase.chatContentDao()


    fun getWaitContentSingle(): Flow<ChatContent> = chatContentDao.getWaitContent()

    fun getChatContentList(
        chatRoomSrl: Long
    ) = chatContentDao.getChatContentList(chatRoomSrl)

    fun getAssistantContent(
        chatRoomSrl: Long,
        chatContentSrl: Long
    ) = chatContentDao.getAssistantContents(chatRoomSrl, chatContentSrl)

    fun isChatContent(
        chatRoomSrl: Long,
        chatContentSrl: Long
    ) = chatContentDao.isResponseChatContent(chatRoomSrl, chatContentSrl)


    fun insertContent(
        data: ChatContent
    ) {
        try {
            chatContentDao.insert(data)
        } catch (_: Exception) { }
    }

    fun insertAllContent(
        data: List<ChatContent>
    ) {
        try {
            chatContentDao.insertAll(data)
        } catch (_: Exception) { }
    }
}