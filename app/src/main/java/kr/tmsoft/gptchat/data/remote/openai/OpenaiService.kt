package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}