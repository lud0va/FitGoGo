package com.example.fitgo.domain.usecases.chat

import com.example.fitgo.data.chat.ConversationRepository
import com.example.fitgo.data.chat.OpenAIRepository
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.chatmodel.MessageStatus
import javax.inject.Inject

class SendChatRequestUseCase  @Inject constructor(private val openAIRepository: OpenAIRepository,
                                                  private val conversationRepository: ConversationRepository
){
    suspend operator fun invoke(
        prompt: String
    ) {
        val message = Message(
            text = prompt,
            isFromUser = true,
            messageStatus = MessageStatus.Sending
        )
        val conversation = conversationRepository.addMessage(message)

        try {
            val reply = openAIRepository.sendChatRequest(conversation)
            conversationRepository.setMessageStatusToSent(message.id)
            conversationRepository.addMessage(reply)
        } catch (exception: Exception) {
            conversationRepository.setMessageStatusToError(message.id)
        }
    }
}