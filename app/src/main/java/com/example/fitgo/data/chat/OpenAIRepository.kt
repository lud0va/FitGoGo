package com.example.fitgo.data.chat


import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.fitgo.domain.chatmodel.Conversation
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.chatmodel.MessageStatus
import javax.inject.Inject

@OptIn(BetaOpenAI::class)
class OpenAIRepository @Inject constructor(private val openAI: OpenAI) {
    @Throws(NoChoiceAvailableException::class)
    suspend fun sendChatRequest(
        conversation: Conversation
    ) : Message {
        return try {
            val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = conversation.toChatMessages()
            )

            val chatMessage = openAI.chatCompletion(chatCompletionRequest).choices.firstOrNull()?.message
                ?: throw NoChoiceAvailableException()

            Message(
                text = chatMessage.content,
                isFromUser = chatMessage.role == ChatRole.User,
                messageStatus = MessageStatus.Sent
            )
        } catch (e: Exception) {
            // Loguea la excepción para entender qué está fallando
            Log.e("OpenAIRepository", "Error sending chat request", e)
            throw e
        }
    }

    private fun Conversation.toChatMessages() = this.list
        .filterNot { it.messageStatus == MessageStatus.Error }
        .map {
            ChatMessage(
                content = it.text,
                role = if (it.isFromUser) { ChatRole.User } else { ChatRole.Assistant }
            )
        }
}

class NoChoiceAvailableException: Exception()
