package com.ubuntuyouiwe.nexus.data.util.dto_type.user_messaging_data

enum class UserMessagingDataFields(val key: String) {
    SentMessages("sentMessages"),
    IsFromCache("isFromCache"),
    HasPendingWrites("hasPendingWrites"),
    TotalCompletionTokens("totalCompletionTokens"),
    TotalPromptTokens("totalPromptTokens"),
    TotalTokens("totalTokens"),
    TotalMessages("totalMessages"),
}