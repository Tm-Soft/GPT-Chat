package kr.tmsoft.gptchat.ui.ChatContentRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.data.remote.openai.CompletionRequest
import kr.tmsoft.gptchat.data.remote.openai.OpenaiMessage
import kr.tmsoft.gptchat.repository.ChatContentLocalRepository
import kr.tmsoft.gptchat.repository.ChatContentRemoteRepository
import timber.log.Timber

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

    fun setChatRoomSrl(srl: Long) {
        _chatRoomSrl.postValue(srl)
    }

    fun addWaitChatContent(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatContentRepo.insertContent(
                0,
                chatRoomSrl = chatRoomSrl.value!!,
                responseChatContentSrl = null,
                role = "user",
                content = message,
                chatId = null,
                totalToken = null,
                status = "wait"
            )
        }
    }
}