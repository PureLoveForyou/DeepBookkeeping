package com.android.deepbookkeeping.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.deepbookkeeping.data.local.entity.ChatMessage
import com.android.deepbookkeeping.databinding.ChatAiMessageRecyclerItemBinding
import com.android.deepbookkeeping.databinding.ChatUserMessageRecyclerItemBinding

class ChatMessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_AI = 2
    }

    private val messages = mutableListOf<ChatMessage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_AI -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ChatAiMessageRecyclerItemBinding.inflate(inflater, parent, false)
                ChatAIMessageViewHolder(binding)
            }
            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ChatUserMessageRecyclerItemBinding.inflate(inflater, parent, false)
                UserMessageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is ChatAIMessageViewHolder -> holder.bind(message)
            is UserMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_AI
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun updateMessage(message: ChatMessage) {
        val index = messages.indexOfFirst { !it.done && !it.isUser }
        if (index != -1) {
            messages[index] = message
            notifyItemChanged(index)
        }
    }

    fun hasMessage(id: Long): Boolean {
        val index = messages.indexOfFirst { it.id == id }
        return index != -1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMessages(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    inner class UserMessageViewHolder(private val binding: ChatUserMessageRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.chatMessageText.text = message.message
            // You can add additional styling or logic for user messages here
        }
    }

    inner class ChatAIMessageViewHolder(private val binding: ChatAiMessageRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.chatMessageText.text = message.message
            // You can add additional styling or logic for AI messages here
            if (message.done) {
                // Hide progress indicator if needed
            } else {
                // Show progress indicator if needed
            }
        }
    }
}