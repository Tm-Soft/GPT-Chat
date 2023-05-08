package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}