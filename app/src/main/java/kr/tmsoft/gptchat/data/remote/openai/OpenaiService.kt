package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}