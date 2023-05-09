package kr.tmsoft.gptchat.ui.ChatRoomList

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tnkfactory.ad.AdError
import com.tnkfactory.ad.AdItem
import com.tnkfactory.ad.AdListener
import com.tnkfactory.ad.InterstitialAdItem
import kotlinx.coroutines.launch
import kr.tmsoft.gptchat.R
import kr.tmsoft.gptchat.adapter.recycler.ChatRoomListAdapter
import kr.tmsoft.gptchat.data.model.ChatRoomModel
import kr.tmsoft.gptchat.databinding.ActivityMainBinding
import kr.tmsoft.gptchat.repository.ChatRoomRepository
import kr.tmsoft.gptchat.service.ChatContentService
import kr.tmsoft.gptchat.ui.ChatContentRoom.ChatContentActivity
import live.lafi.library_dialog.Dialog
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ChatRoomViewModel

    private var mChatRoomListAdapter: ChatRoomListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Timber 초기화 작업.
        Timber.plant(Timber.DebugTree())
        
        // 서버 통신 서비스 실행
        startService(
            Intent(applicationContext, ChatContentService::class.java)
        )
        Timber.e("데이터 : 서비스 실행")
    }


    override fun onStart() {
        super.onStart()

        binding.bannerAdView.load()

        viewModel = ChatRoomViewModel(
            ChatRoomRepository(application)
        )

        lifecycle.coroutineScope.launch {
            viewModel.getChatRoomAllData().collect { itemModel ->
                if (itemModel.isEmpty()) {
                    viewModel.addDefaultChatRoom()
                } else {
                    val chatRoomList = ArrayList<ChatRoomModel>()
                    itemModel.forEach {
                        chatRoomList.add(
                            ChatRoomModel(
                                it.chatRoomSrl,
                                it.chatTitle,
                                it.chatContent,
                                it.profileUri,
                                it.lastViewDate,
                                it.lastUpdate
                            )
                        )
                    }

                    setAdapter(chatRoomList)
                }
            }
        }

        initUiEvent()
    }

    private fun setAdapter(list: List<ChatRoomModel>) {
        if (mChatRoomListAdapter == null) {
            mChatRoomListAdapter = ChatRoomListAdapter { chatRoomSrl ->
                // chatRoomSrl 토대로 ChatRoom Activity 실행하기.
                val intent = Intent(this, ChatContentActivity::class.java)
                intent.putExtra("chatRoomSrl", chatRoomSrl)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_silde_in_left, R.anim.anim_silde_out_right)
            }

            binding.recyclerChatList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = mChatRoomListAdapter
            }
        }

        mChatRoomListAdapter?.submitList(list)
    }

    private fun initUiEvent() {
        binding.layoutChatListAdd.setOnClickListener {
            showFullScreenAD()
        }
    }

    private fun showFullScreenAD() {
        InterstitialAdItem(
            this,
            "TEST_INTERSTITIAL_V",
            object: AdListener() {
                override fun onError(p0: AdItem?, p1: AdError?) {
                    super.onError(p0, p1)
                    showAddChatRoomDialog()
                }

                override fun onClose(p0: AdItem?, p1: Int) {
                    super.onClose(p0, p1)
                    showAddChatRoomDialog()
                }

                override fun onLoad(p0: AdItem?) {
                    p0?.show()
                    super.onLoad(p0)
                    binding.recyclerChatList.smoothScrollToPosition(0)
                }
            }
        ).load()
    }

    private fun showAddChatRoomDialog() {
        Dialog.with(this)
            .title("채팅방 이름 입력")
            .content("GPT 비서")
            .positiveText("확인")
            .negativeText("취소")
            .stringCallbackListener { text ->
                if (text.isEmpty())
                    Toast.makeText(this, "채팅방 이름을 입력 해주세요.", Toast.LENGTH_LONG).show()
                else {
                    viewModel.addChatRoom(text)
                }
            }
            .showEditTextDialog()
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(
            Intent(applicationContext, ChatContentService::class.java)
        )
    }
}