package com.example.fitgo.domain.chatmodel

sealed class MessageStatus {
    object Sending: MessageStatus()
    object Error: MessageStatus()
    object Sent: MessageStatus()
}