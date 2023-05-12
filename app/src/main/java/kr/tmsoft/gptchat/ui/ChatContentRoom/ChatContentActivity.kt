package kr.tmsoft.gptchat.ui.ChatContentRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.R
import kr.tmsoft.gptchat.adapter.recycler.ChatContentListAdapter
import kr.tmsoft.gptchat.data.model.ChatContentModel
import kr.tmsoft.gptchat.databinding.ActivityChatContentBinding
import kr.tmsoft.gptchat.repository.ChatContentLocalRepository
import timber.log.Timber

class ChatContentActivity : AppCompatActivity() {
    private val backPressedCallBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            if (isFinishing) {
                overridePendingTransition(R.anim.anim_silde_in_right, R.anim.anim_silde_out_left)
            }
        }
    }

    private lateinit var binding: ActivityChatContentBinding

    private lateinit var viewModel: ChatContentViewModel

    private var mChatContentAdapter: ChatContentListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ChatContentViewModelFactory(ChatContentLocalRepository(application))
        )[ChatContentViewModel::class.java]

        this.onBackPressedDispatcher.addCallback(this, backPressedCallBack)

        if (intent.hasExtra("chatRoomSrl")) {
            viewModel.setChatRoomSrl(
                intent.getLongExtra("chatRoomSrl", 0)
            )
        } else {
            Timber.e("데이터 전달된게 없어.")
        }


        binding.layoutBtnSend.setOnClickListener {
            val msg = binding.editTextMessage.text.toString()
            viewModel.addWaitChatContent(msg)
            binding.editTextMessage.text = null
        }

        viewModel.chatRoomSrl.observe(this) {
            startRoomObserve()
        }

        startUiObserve()
    }

    //TODO
    // ChatContent 에서 user 질문 사항을 가져온다.
    // user 질문 사항이 wait, fail, complete 구분한다.
    // complete의 경우 assistant 답변을 가져온다.
    @OptIn(DelicateCoroutinesApi::class)
    private fun startRoomObserve() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getChatContentList().collect {
                Timber.d("ChatContentList: $it")


            }
        }
    }

    private fun startUiObserve() {
        viewModel.chatContentList.observe(this) {
            setChatContentAdapter(it)
        }

        viewModel.setChatContentList(
            listOf(
                ChatContentModel(
                    1,
                    "assistant",
                    "안녕하세요. 저는 당신의 AI 비서 입니다.\n무엇을 도와드릴까요?",
                    null
                ),
                ChatContentModel(
                    2,
                    "user",
                    "Tnk Factory에 대해서 알려줄래?",
                    null
                ),
            )
        )
    }

    private fun setChatContentAdapter(list: List<ChatContentModel>) {
        if (mChatContentAdapter == null) {
            mChatContentAdapter = ChatContentListAdapter()

            binding.recyclerChatContent.apply {
                adapter = mChatContentAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        mChatContentAdapter?.submitList(list)
    }

}
