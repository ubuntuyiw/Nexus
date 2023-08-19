package com.ubuntuyouiwe.nexus.domain.model.messages

import com.google.firebase.Timestamp

data class Messages(
    val id: String = "",
    val messages: List<MessageItem> = emptyList(),
    val created: Timestamp = Timestamp.now(),
    val hasPendingWrites: Boolean = false,
)