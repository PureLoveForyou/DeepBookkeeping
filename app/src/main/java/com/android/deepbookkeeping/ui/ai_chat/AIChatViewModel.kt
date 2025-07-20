package com.android.deepbookkeeping.ui.ai_chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.ChatMessage
import com.android.deepbookkeeping.data.repository.AccountingRepository
import com.android.deepbookkeeping.llm_inference.InferenceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AIChatViewModel @Inject constructor(
    private val repository: AccountingRepository,
    private val inferenceModel: InferenceModel
) : ViewModel() {
    var inputEnable: MutableLiveData<Boolean> = MutableLiveData(true)
    var chatMessage: MutableLiveData<ChatMessage?> = MutableLiveData()

    fun sendMessage(message: String) {
        if (inputEnable.value == false) return
        chatMessage.postValue(null)
        var chatId: Long = -1
        viewModelScope.launch(Dispatchers.IO) {
            inferenceModel.generateResponseAsync(message) { partial, done ->
                if (chatMessage.value == null) {
                    chatId = System.currentTimeMillis()
                }
                val msgWhole = (chatMessage.value?.message ?: "") + partial
                chatMessage.postValue(
                    ChatMessage(
                        id = chatId,
                        done = done,
                        message = msgWhole,
                        partialMsg = partial
                    )
                )
                Log.d(TAG, "generateResponseAsync: ChatState = $chatMessage")
                inputEnable.postValue(done)
            }
        }
        inputEnable.postValue(false)
    }

    companion object {
        const val TAG = Constants.TAG_PREFIX + "AIChatViewModel"
    }
}