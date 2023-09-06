package com.ubuntuyouiwe.nexus.data.dto.messages

import com.google.firebase.Timestamp

data class MessagesDto(
    val id: String? = null,
    val messages: List<MessageItemDto> = emptyList(),
    val created: Timestamp? = Timestamp.now(),
    val totalTokens: Double? = null,
    val updateTimestamp: Timestamp = Timestamp.now(),
    val hasPendingWrites: Boolean = false
)