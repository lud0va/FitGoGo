package com.example.fitgo.domain.chatmodel

import java.util.UUID

data class Message(
    val id:String= UUID.randomUUID().toString(),
    val text: String,
    val isFromUser: Boolean,
    val messageStatus: MessageStatus = MessageStatus.Sending
)
