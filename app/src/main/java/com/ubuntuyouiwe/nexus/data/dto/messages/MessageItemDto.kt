package com.ubuntuyouiwe.nexus.data.dto.messages

import kotlinx.serialization.Serializable


@Serializable
data class MessageItemDto(
    val content: String = "",
    val role: String = ""
)