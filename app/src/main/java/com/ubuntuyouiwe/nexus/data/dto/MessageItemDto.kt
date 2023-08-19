package com.ubuntuyouiwe.nexus.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageItemDto(
    val content: String? = null,
    val role: String? = null
)