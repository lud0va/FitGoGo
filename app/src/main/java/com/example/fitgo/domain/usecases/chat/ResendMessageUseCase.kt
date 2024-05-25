package com.example.fitgo.domain.usecases.chat

import com.example.fitgo.data.chat.ConversationRepository
import com.example.fitgo.data.chat.OpenAIRepository
import com.example.fitgo.domain.chatmodel.Message
import javax.inject.Inject

class ResendMessageUseCase  @Inject constructor(private val openAIRepository: OpenAIRepository,
                                                 private val conversationRepository: ConversationRepository
) {
    suspend operator fun invoke(
        message: Message
    ) {
        val conversation = conversationRepository.resendMessage(message)

        try {
            val reply = openAIRepository.sendChatRequest(conversation)
            conversationRepository.setMessageStatusToSent(message.id)
            conversationRepository.addMessage(reply)
        } catch (exception: Exception) {
            conversationRepository.setMessageStatusToError(message.id)
        }
    }
}