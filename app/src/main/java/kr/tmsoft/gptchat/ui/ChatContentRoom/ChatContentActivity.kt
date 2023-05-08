package kr.tmsoft.gptchat.ui.ChatContentRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tmsoft.gptchat.R
import kr.tmsoft.gptchat.databinding.ActivityChatContentBinding
import kr.tmsoft.gptchat.repository.ChatContentLocalRepository
import kr.tmsoft.gptchat.repository.ChatContentRemoteRepository
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
            viewModel.setChatRoomSrl(intent.getLongExtra("chatRoomSrl", 0))
        } else {
            Timber.e("데이터 전달된게 없어.")
        }

        viewModel.chatRoomSrl.observe(this) {
            viewModel.addWaitChatContent("티앤케이 팩토리는 광고 플랫폼인데 다시한번 제대로 알려줘")
        }
    }

}
