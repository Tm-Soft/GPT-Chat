package kr.tmsoft.gptchat.data.remote.openai

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenaiAPI {
    @POST("/v1/chat/completions")
    suspend fun getCompletion(
        @Body requestBody: CompletionRequest
    ): Response<CompletionResponse>
}