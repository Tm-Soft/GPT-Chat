package kr.tmsoft.gptchat.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.data.remote.openai.CompletionRequest
import kr.tmsoft.gptchat.data.remote.openai.OpenaiMessage
import kr.tmsoft.gptchat.repository.ChatContentLocalRepository
import kr.tmsoft.gptchat.repository.ChatContentRemoteRepository
import kr.tmsoft.gptchat.repository.ChatRoomRepository
import timber.log.Timber

class ChatContentService : Service() {
    private var mChatContentLocalRepo: ChatContentLocalRepository? = null
    private var mChatContentRemoteRepo: ChatContentRemoteRepository? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        mChatContentLocalRepo = ChatContentLocalRepository(application)
        mChatContentRemoteRepo = ChatContentRemoteRepository()

        GlobalScope.launch(Dispatchers.IO) {
            /*
                ChatContent 에 등록 된 wait Content 서버로 질문 보내기.
            */
            mChatContentLocalRepo?.getWaitContentSingle()!!
                .filterNotNull()
                .collect {
                    // chatRoom에 보내기...

                    val requestMessage = ArrayList<OpenaiMessage>()
                    requestMessage.add(
                        OpenaiMessage(
                            role = "system",
                            content = "You are my assistant, please answer as concisely and accurately as possible"
                        )
                    )

                    val beforeAssistant = mChatContentLocalRepo?.getAssistantContent(it.chatRoomSrl)
                    beforeAssistant?.forEach { before ->
                        requestMessage.add(
                            OpenaiMessage(
                                "assistant",
                                before.content
                            )
                        )
                    }

                    requestMessage.add(
                            OpenaiMessage(
                            role = "user",
                            content = it.content
                        )
                    )

                    val body = CompletionRequest(
                        model = "gpt-3.5-turbo",
                        temperature = 0.9,
                        messages = requestMessage
                    )

                    Timber.e("전송 데이터 : $body")


                    val response = mChatContentRemoteRepo?.sendCompletionRequest(it.chatContentSrl, body)
                    Timber.e("코루틴 데이터 반환 값 : $response")
                    if (response != null && response.error == null) {
                        // 결과값 반환 성공.

                        // 반환이 성공 된 질문(role:user)의 status :: complete 로 update
                        mChatContentLocalRepo?.insertContent(
                            chatContentSrl = response.chatContentSrl,
                            chatRoomSrl = it.chatRoomSrl,
                            responseChatContentSrl = null,
                            role = "user",
                            content = it.content,
                            chatId = null,
                            totalToken = null,
                            status = "complete"
                        )

                        // 반환 받은 Content :: Insert
                        mChatContentLocalRepo?.insertContent(
                            chatContentSrl = 0,
                            chatRoomSrl = it.chatRoomSrl,
                            responseChatContentSrl = response.chatContentSrl,
                            role = "assistant",
                            content = response.choices?.get(0)!!.message.content,
                            chatId = response.id,
                            totalToken = response.usage?.total_tokens!!.toLong(),
                            status = "complete"
                        )

                    } else {
                        // 결과값 반환 실패
                        if (response != null)
                            mChatContentLocalRepo?.insertContent(
                                chatContentSrl = response.chatContentSrl,
                                chatRoomSrl = it.chatRoomSrl,
                                responseChatContentSrl = null,
                                role = "user",
                                content = it.content,
                                chatId = null,
                                totalToken = null,
                                status = "fail"
                            )
                    }
                }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}