package com.ubuntuyouiwe.nexus.domain.model.messages

data class Message(
    val messages: List<Messages> = emptyList(),
    val isFromCache: Boolean = false
)