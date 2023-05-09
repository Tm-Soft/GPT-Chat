package kr.tmsoft.gptchat.adapter.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.tmsoft.gptchat.data.model.ChatContentModel
import kr.tmsoft.gptchat.databinding.ItemChatContentLeftTextBinding
import kr.tmsoft.gptchat.databinding.ItemChatContentRightTextBinding
import java.lang.IndexOutOfBoundsException

class ChatContentListAdapter(

): ListAdapter<ChatContentModel, RecyclerView.ViewHolder>(
    diffUtil
) {
    private var context: Context? = null

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).role) {
            "assistant" -> 1
            "user" -> 2
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (this.context == null)
            this.context = parent.context

        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            1 -> LeftTextViewHolder(
                ItemChatContentLeftTextBinding.inflate(layoutInflater, parent, false)
            )
            2 -> RightTextViewHolder(
                ItemChatContentRightTextBinding.inflate(layoutInflater, parent, false)
            )

            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemModel = getItem(position)

        when(holder) {
            is LeftTextViewHolder -> {
                val binding = holder.binding

                binding.textViewTextContent.text = itemModel.content
            }

            is RightTextViewHolder -> {
                val binding = holder.binding

                binding.textViewTextContent.text = itemModel.content
            }
        }
    }


    inner class LeftTextViewHolder(
        val binding: ItemChatContentLeftTextBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    inner class RightTextViewHolder(
        val binding: ItemChatContentRightTextBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatContentModel>() {
            override fun areItemsTheSame(
                oldItem: ChatContentModel,
                newItem: ChatContentModel
            ) = oldItem.chatContentSrl == newItem.chatContentSrl


            override fun areContentsTheSame(
                oldItem: ChatContentModel,
                newItem: ChatContentModel
            ) = oldItem == newItem

        }
    }
}