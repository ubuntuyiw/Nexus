package com.ubuntuyouiwe.nexus.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val name: String? = null,
    val totalCompletionTokens: Double? = null,
    val totalPromptTokens: Double? = null,
    val totalTokens: Double? = null
)
