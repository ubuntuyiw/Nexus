package com.ubuntuyouiwe.nexus.data.dto

import com.google.firebase.Timestamp

data class MessagesDto(
    val id: String? = null,
    val messages: List<MessageItemDto> = emptyList(),
    val created: Timestamp? = Timestamp.now(),
)