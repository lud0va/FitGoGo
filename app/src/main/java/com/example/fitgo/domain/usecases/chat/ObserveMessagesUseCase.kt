package com.example.fitgo.domain.usecases.chat

import com.example.fitgo.data.chat.ConversationRepository
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(  private val conversationRepository: ConversationRepository) {
    operator fun invoke() = conversationRepository.conversationFlow
}