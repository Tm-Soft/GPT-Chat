package kr.tmsoft.gptchat.ui.ChatContentRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.data.model.ChatContentModel
import kr.tmsoft.gptchat.data.room.entity.ChatContent
import kr.tmsoft.gptchat.repository.ChatContentLocalRepository
import kr.tmsoft.gptchat.util.DateConverter

@Suppress("UNCHECKED_CAST")
class ChatContentViewModelFactory(
    private val chatContentRepo: ChatContentLocalRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChatContentViewModel::class.java)) {
            ChatContentViewModel(chatContentRepo) as T
        } else {
            throw IllegalAccessException()
        }
    }
}

class ChatContentViewModel(
    private val chatContentRepo: ChatContentLocalRepository
): ViewModel() {
    private val _chatRoomSrl = MutableLiveData<Long>()
    val chatRoomSrl: LiveData<Long> = _chatRoomSrl

    private val _chatContentList = MutableLiveData<List<ChatContentModel>>()
    val chatContentList: LiveData<List<ChatContentModel>> = _chatContentList

    fun setChatRoomSrl(srl: Long) {
        _chatRoomSrl.postValue(srl)
    }

    fun setChatContentList(list: List<ChatContentModel>) {
        _chatContentList.postValue(list)
    }

    fun getChatContentList(): Flow<List<ChatContent>> {
        return chatContentRepo.getChatContentList(chatRoomSrl.value!!)
    }
    fun addWaitChatContent(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatContentRepo.insertContent(
                ChatContent(
                    0,
                    chatRoomSrl = chatRoomSrl.value!!,
                    responseChatContentSrl = null,
                    role = "user",
                    content = message,
                    chatId = null,
                    promptToken = null,
                    completionToken = null,
                    status = "wait",
                    lastUpdate = DateConverter().getFullDate()
                )
            )
        }
    }
}