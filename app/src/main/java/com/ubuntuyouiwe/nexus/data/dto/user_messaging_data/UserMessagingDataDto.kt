package com.ubuntuyouiwe.nexus.data.dto.user_messaging_data

data class UserMessagingDataDto(
    val sentMessages: Double? = null,
    val isFromCache: Boolean? = null,
    val hasPendingWrites: Boolean? = null,
    @field:JvmField
    val totalCompletionTokens: Double? = null,
    @field:JvmField
    val totalPromptTokens: Double? = null,
    @field:JvmField
    val totalTokens: Double? = null,
    @field:JvmField
    val totalMessages: Double? = null,
)
