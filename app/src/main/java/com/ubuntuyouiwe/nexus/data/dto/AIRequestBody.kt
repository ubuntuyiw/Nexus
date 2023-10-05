package com.ubuntuyouiwe.nexus.data.dto

import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AIRequestBody(
    val model: String = "gpt-3.5-turbo",
    val messages: List<MessageItemDto>,
    val max_tokens: Int = 1000,
    val temperature: Int = 0
)
