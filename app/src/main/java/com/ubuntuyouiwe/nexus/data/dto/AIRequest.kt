package com.ubuntuyouiwe.nexus.data.dto

import com.ubuntuyouiwe.nexus.data.dto.user.UserInfoDto
import kotlinx.serialization.Serializable

@Serializable
data class AIRequest(
    val aiRequestBody: AIRequestBody,
    val chatRoomId: String,
    val info: List<MessageItemDto>,
    val messageId: String,
    val ownerId: String
)