package com.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitgo.data.chat.ConversationRepository
import com.example.fitgo.data.chat.OpenAIRepository
import com.example.fitgo.domain.usecases.chat.ObserveMessagesUseCase
import com.example.fitgo.domain.usecases.chat.ResendMessageUseCase
import com.example.fitgo.domain.usecases.chat.SendChatRequestUseCase
import com.example.fitgo.ui.chat.ChatViewModel

