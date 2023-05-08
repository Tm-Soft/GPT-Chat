package kr.tmsoft.gptchat.data.remote.openai

data class CompletionRequest(
    val model: String,
    val temperature: Double,
    val messages: List<OpenaiMessage>
)

data class CompletionResponse(
    val chatContentSrl: Long,
    val id: String?,
    val usage: OpenaiUsage?,
    val choices: List<OpenaiChoices>?,
    val error: OpenaiError?
)

data class OpenaiUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class OpenaiChoices(
    val message: OpenaiMessage
)

data class OpenaiMessage(
    val role: String,
    val content: String
)

data class OpenaiError(
    val message: String,
    val type: String,
    val param: String?,
    val code: String?
)
