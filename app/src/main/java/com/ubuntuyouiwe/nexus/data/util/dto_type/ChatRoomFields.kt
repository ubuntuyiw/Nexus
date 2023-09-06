package com.ubuntuyouiwe.nexus.data.util.dto_type

enum class ChatRoomFields(val key: String) {
    ID("id"),
    NAME("name"),
    OWNER_ID("ownerId"),
    CREATION_DATE("creationDate"),
    IS_FAVORITED("isFavorited"),
    IS_ARCHIVED("isArchived"),
    IS_PINNED("isPinned"),
    ROLE_ID("roleId"),
    TOTAL_MESSAGE_COUNT("totalMessageCount"),
    LAST_MESSAGE("lastMessage"),
    LAST_MESSAGE_DATE("lastMessageDate"),
    ROLE("role")
}
