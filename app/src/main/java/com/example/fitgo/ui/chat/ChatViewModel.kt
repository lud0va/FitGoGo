package com.example.fitgo.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.domain.chatmodel.Conversation
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.chatmodel.MessageStatus
import com.example.fitgo.domain.usecases.chat.ObserveMessagesUseCase
import com.example.fitgo.domain.usecases.chat.ResendMessageUseCase
import com.example.fitgo.domain.usecases.chat.SendChatRequestUseCase
import com.example.fitgo.ui.dietas.DietasContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ChatViewModel  @Inject constructor(
    private val sendChatRequestUseCase: SendChatRequestUseCase,
    private val resendChatRequestUseCase: ResendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow((ChatContract.State()))
    val uistate: StateFlow<ChatContract.State> = _uiState.asStateFlow()
    init {
        observeMessageList()
    }


    fun event(event: ChatContract.Event) {
        when (event) {
            is ChatContract.Event.onResendMessage -> resendMessage(event.message)
            is ChatContract.Event.onSendMessage -> sendMessage(event.message)
        }
    }
    private fun observeMessageList() {
        viewModelScope.launch {
            observeMessagesUseCase.invoke().collect { conversation ->
                _uiState.update {
                    uistate.value.copy(conversation=conversation, _isSendingMessage =   conversation.list.any { it.messageStatus == MessageStatus.Sending } )
                }




            }
        }
    }

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            sendChatRequestUseCase(
                prompt
            )
        }
    }

    fun resendMessage(message: Message) {
        viewModelScope.launch {
            resendChatRequestUseCase(
                message
            )
        }
    }
}