package com.ubuntuyouiwe.nexus.data.dto.messages


data class MessageDto(
    val messages: List<MessagesDto> = emptyList(),
    val isFromCache: Boolean = false
)