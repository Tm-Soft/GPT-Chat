package kr.tmsoft.gptchat.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.tmsoft.gptchat.data.model.ChatRoomModel
import kr.tmsoft.gptchat.databinding.ItemChatRoomListBinding
import timber.log.Timber

class ChatRoomListAdapter(
    private val onClick: (Long) -> Unit
): ListAdapter<ChatRoomModel, ChatRoomListAdapter.ChatRoomListViewHolder>(
    diffUtil
) {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListViewHolder {
        val binding = ItemChatRoomListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        if (this.context == null)
            this.context = parent.context

        return ChatRoomListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomListViewHolder, position: Int) {
        val binding = holder.binding
        val itemModel = getItem(position)

        itemModel.title.let { binding.chatRoomTitle.text = it }
        itemModel.content.let { binding.chatRoomContent.text = it }

        Timber.d("lastUpdate : ${itemModel.lastUpdate} // lastView : ${itemModel.lastViewDate}")

        if (itemModel.lastViewDate == null
            || itemModel.lastUpdate > itemModel.lastViewDate) {
            binding.layoutNewIcon.visibility = View.VISIBLE
        } else
            binding.layoutNewIcon.visibility = View.GONE
    }

    inner class ChatRoomListViewHolder(
        val binding: ItemChatRoomListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(
                    getItem(adapterPosition).chatRoomSrl
                )
            }
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<ChatRoomModel>() {
            override fun areItemsTheSame(
                oldItem: ChatRoomModel,
                newItem: ChatRoomModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ChatRoomModel,
                newItem: ChatRoomModel
            ): Boolean {
                return oldItem.chatRoomSrl == newItem.chatRoomSrl
            }

        }
    }
}