package com.ubuntuyouiwe.nexus.data.dto

import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import kotlinx.serialization.Serializable

@Serializable
data class AIRequestBody(
    val model: String,
    val messages: List<MessageItemDto>
)
