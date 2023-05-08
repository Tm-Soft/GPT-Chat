package kr.tmsoft.gptchat.repository

import kr.tmsoft.gptchat.data.remote.openai.CompletionRequest
import kr.tmsoft.gptchat.data.remote.openai.CompletionResponse
import kr.tmsoft.gptchat.data.remote.openai.OpenaiError
import kr.tmsoft.gptchat.data.remote.openai.OpenaiService
import timber.log.Timber
import java.lang.Exception

class ChatContentRemoteRepository {
    suspend fun sendCompletionRequest(
        chatContentSrl: Long,
        request: CompletionRequest
    ): CompletionResponse {
        return try {
            val response = OpenaiService.client.getCompletion(request)
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                CompletionResponse(
                    chatContentSrl = chatContentSrl,
                    id = responseBody.id,
                    usage = responseBody.usage,
                    choices = responseBody.choices,
                    null
                )
            } else
                CompletionResponse(
                    chatContentSrl,
                    null,
                    null,
                    null,
                    OpenaiError(
                        message = "noneBody",
                        type = "noneType",
                        null,
                        null
                    )
                )
        } catch (e: Exception) {
            Timber.e("네트워크 실패 : $e")
            CompletionResponse(
                chatContentSrl,
                null,
                null,
                null,
                OpenaiError(
                    message = "disconnect",
                    type = "disconnect",
                    null,
                    null
                )
            )
        }
    }
}