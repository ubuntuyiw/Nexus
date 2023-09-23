package com.ubuntuyouiwe.nexus.domain.model.user_messaging_data

data class UserMessagingData(
    val isFromCache: Boolean = false,
    val hasPendingWrites: Boolean = false,
    val totalMessages: Int = 0
)
