package com.example.fitgo.data.chat

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.client.OpenAI
import com.example.fitgo.domain.chatmodel.Conversation
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.chatmodel.MessageStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConversationRepository {
    private var messagesList = mutableListOf(
        Message(
            text = "Saludos, cómo puedo ayudarte?",
            isFromUser = false,
            messageStatus = MessageStatus.Sent
        )
    )

    private val _conversationFlow = MutableStateFlow(
        value = Conversation(list = messagesList)
    )
    val conversationFlow = _conversationFlow.asStateFlow()

    fun addMessage(message: Message) : Conversation {
        messagesList.add(message)
        return updateConversationFlow(messagesList)
    }

    fun resendMessage(message: Message) : Conversation {
        messagesList.remove(message)
        messagesList.add(message)
        return updateConversationFlow(messagesList)
    }

    fun setMessageStatusToSent(messageId: String) {
        val index = messagesList.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messagesList[index] = messagesList[index].copy(messageStatus = MessageStatus.Sent)
        }

        updateConversationFlow(messagesList)
    }

    fun setMessageStatusToError(messageId: String) {
        val index = messagesList.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messagesList[index] = messagesList[index].copy(messageStatus = MessageStatus.Error)
        }

        updateConversationFlow(messagesList)
    }

    private fun updateConversationFlow(messagesList: List<Message>) : Conversation {
        val conversation = Conversation(
            list = messagesList
        )
        _conversationFlow.value = conversation

        return conversation
    }
}