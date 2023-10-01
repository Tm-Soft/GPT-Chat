package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}