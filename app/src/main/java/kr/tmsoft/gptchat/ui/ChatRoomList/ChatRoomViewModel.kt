package kr.tmsoft.gptchat.ui.ChatRoomList


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.data.room.entity.ChatRoom
import kr.tmsoft.gptchat.repository.ChatRoomRepository
import kr.tmsoft.gptchat.util.DateConverter

class ChatRoomViewModel(
    val chatRoomRepo: ChatRoomRepository
): ViewModel() {
    suspend fun getChatRoomAllData(): Flow<List<ChatRoom>> = chatRoomRepo.getAllChatRoom()

    private fun insertChatRoomData(chatRoom: ChatRoom) {
        viewModelScope.launch(Dispatchers.Default) {
            chatRoomRepo.insert(chatRoom)
        }
    }

    fun addChatRoom(
        title: String
    ) {
        this.insertChatRoomData(
            ChatRoom(
                0,
                title,
                "안녕하세요! 저는 AI 어시스턴트입니다. 무엇을 도와드릴까요?",
                null,
                null,
                DateConverter().getFullDate()
            )
        )
    }

    fun addDefaultChatRoom() {
        this.insertChatRoomData(
            ChatRoom(
                0,
                "GPT 채팅 비서",
                "안녕하세요! 저는 AI 어시스턴트입니다. 무엇을 도와드릴까요?",
                null,
                null,
                DateConverter().getFullDate()
            )
        )
    }
}