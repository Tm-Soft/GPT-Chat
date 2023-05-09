package kr.tmsoft.gptchat.ui.ChatRoomList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.data.room.entity.ChatRoom
import kr.tmsoft.gptchat.repository.ChatRoomRepository
import kr.tmsoft.gptchat.util.DateConverter
import timber.log.Timber

class ChatRoomViewModel(
    val chatRoomRepo: ChatRoomRepository
): ViewModel() {
    fun getChatRoomAllData(): Flow<List<ChatRoom>> = chatRoomRepo.getAllChatRoom()

    fun getChatLastContent(chatRoomSrl: Long) = chatRoomRepo.getChatLastContent(chatRoomSrl)

    fun getQuestionContent(contentSrl: Long) = chatRoomRepo.getQuestionContent(contentSrl)

    private fun insertChatContentData(chatContent: ChatContent) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRoomRepo.insertChatContent(chatContent)
        }
    }

    fun addChatRoom(
        title: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val chatRoomSrl = chatRoomRepo.insertChatRoom(
                ChatRoom(
                    chatRoomSrl = 0,
                    chatTitle = title,
                    null,
                    null,
                    DateConverter().getFullDate()
                )
            )

            chatRoomRepo.insertChatContent(
                ChatContent(
                    chatContentSrl = 0,
                    chatRoomSrl = chatRoomSrl,
                    responseChatContentSrl = null,
                    role = "assistant",
                    content = "안녕하세요. 저는 당신의 AI 비서 입니다.\n무엇을 도와드릴까요?",
                    chatId = null,
                    promptToken = null,
                    completionToken = null,
                    status = "complete",
                    lastUpdate = DateConverter().getFullDate()
                )
            )
            Timber.e("생성된 데이터 Chat Room Srl : $chatRoomSrl")

        }
    }

    fun addDefaultChatRoom() {
        addChatRoom("GPT 채팅 봇")
    }
}