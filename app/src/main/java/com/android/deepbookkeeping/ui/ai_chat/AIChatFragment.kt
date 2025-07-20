package com.android.deepbookkeeping.ui.ai_chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.deepbookkeeping.adapter.ChatMessageAdapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.ChatMessage
import com.android.deepbookkeeping.databinding.FragmentAiChatBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AIChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AIChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewmodel: AIChatViewModel by viewModels()
    private lateinit var binding: FragmentAiChatBinding
    private lateinit var chatMessageAdapter: ChatMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAiChatBinding.inflate(inflater, container, false)
        initRecyclerView()
        initClickListener()
        initObserver()
        return binding.root
    }

    private fun initRecyclerView() {
        chatMessageAdapter = ChatMessageAdapter()
        binding.chatHistoryRecyclerView.apply {
            adapter = chatMessageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initObserver() {
        viewmodel.chatMessage.observe(viewLifecycleOwner) { chatMessage ->
            Log.d(TAG, "receive AI message: $chatMessage")
            chatMessage?.let {
                if (chatMessageAdapter.hasMessage(it.id)) {
                    chatMessageAdapter.updateMessage(it)
                } else {
                    chatMessageAdapter.addMessage(it)
                }
            }
            binding.chatHistoryRecyclerView.smoothScrollToPosition(chatMessageAdapter.itemCount - 1)
        }
        viewmodel.inputEnable.observe(viewLifecycleOwner) { inputMode ->
            binding.sendUserMessage.visibility = if (inputMode) View.VISIBLE else View.GONE
        }
    }

    private fun initClickListener() {
        binding.sendUserMessage.setOnClickListener {
            val userMessage = binding.inputMessage.text.toString()
            viewmodel.sendMessage(userMessage)
            chatMessageAdapter.addMessage(
                ChatMessage(
                    id = System.currentTimeMillis(),
                    isUser = true,
                    message = userMessage,
                    done = true
                )
            )
            binding.inputMessage.setText("")
        }
    }

    companion object {
        const val TAG = Constants.TAG_PREFIX + "AIChatFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AIChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AIChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}