package com.ubuntuyouiwe.nexus.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoDto(
    val name: String? = null,
    @field:JvmField
    val totalCompletionTokens: Double? = null,
    @field:JvmField
    val totalPromptTokens: Double? = null,
    @field:JvmField
    val totalTokens: Double? = null,
    val ownerId: String? = null,
    val id: String? = null,
)
