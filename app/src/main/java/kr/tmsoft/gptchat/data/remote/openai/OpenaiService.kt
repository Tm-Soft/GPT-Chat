package kr.tmsoft.gptchat.data.remote.openai

import kr.tmsoft.gptchat.data.remote.RetrofitClient

object OpenaiService {
    private const val baseUrl = "https://api.openai.com"
    var client = RetrofitClient()
        .defaultRetrofit(baseUrl, "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")
        .create(OpenaiAPI::class.java)
}