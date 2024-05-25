package com.example.fitgo.ui.chat

import com.example.fitgo.domain.chatmodel.Conversation
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.model.Dieta

interface ChatContract {
    sealed class Event(){
        class onSendMessage(val message:String):Event()
        class onResendMessage(val message: Message):Event()

    }
    data class State(
        val _isSendingMessage:Boolean=false,
        val conversation: Conversation= Conversation(emptyList())
    )
}