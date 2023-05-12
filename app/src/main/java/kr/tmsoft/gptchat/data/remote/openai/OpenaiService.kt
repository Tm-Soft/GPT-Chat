package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}