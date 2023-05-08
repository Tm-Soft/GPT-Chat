package kr.tmsoft.gptchat.repository

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kr.tmsoft.gptchat.data.room.OpenAiDatabase
import kr.tmsoft.gptchat.data.room.dao.ChatContentDao
import kr.tmsoft.gptchat.data.room.dao.ChatRoomDao
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.data.room.entity.ChatRoom
import kr.tmsoft.gptchat.util.DateConverter
import timber.log.Timber

class ChatContentLocalRepository(application: Application) {
    private val openAiDatabase = OpenAiDatabase.getInstance(application)!!
    private val chatContentDao: ChatContentDao = openAiDatabase.chatContentDao()


    fun getWaitContentSingle(): Flow<ChatContent> = chatContentDao.getWaitContent()

    fun getAssistantContent(chatRoomSrl: Long): List<ChatContent> {
        return chatContentDao.getAssistantContents(chatRoomSrl)
    }

    fun insertContent(
        chatContentSrl: Long = 0,
        chatRoomSrl: Long,
        responseChatContentSrl: Long? = null,
        role: String,
        content: String,
        chatId: String? = null,
        totalToken: Long? = null,
        status: String
    ) {
        val data = ChatContent(
            chatContentSrl = chatContentSrl,
            chatRoomSrl = chatRoomSrl,
            responseChatContentSrl = responseChatContentSrl,
            role = role,
            content = content,
            chatId = chatId,
            totalToken = totalToken,
            lastUpdate = DateConverter().getFullDate(),
            status = status,
        )
        try {
            chatContentDao.insert(
                data
            )
        } catch (_: Exception) { }

    }
}