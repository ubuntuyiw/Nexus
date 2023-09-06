package com.ubuntuyouiwe.nexus.domain.model.messages

import com.google.firebase.Timestamp

data class Messages(
    val id: String = "",
    val messages: List<MessageItem> = emptyList(),
    val created: Timestamp = Timestamp.now(),
    val totalTokens: Double = 0.0,
    val hasPendingWrites: Boolean = false,

    val isSpeak: Boolean = false,
    val codeLanguage: String = "",
)