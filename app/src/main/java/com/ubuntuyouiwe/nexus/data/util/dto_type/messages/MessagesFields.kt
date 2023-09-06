package com.ubuntuyouiwe.nexus.data.util.dto_type.messages

enum class MessagesFields(val key: String) {
    ID("id"),
    MESSAGES("messages"),
    CREATED("created"),
    TOTAL_TOKENS("totalTokens"),
    UPDATE_TIMESTAMP("updateTimestamp"),
    HAS_PENDING_WRITES("hasPendingWrites")
}