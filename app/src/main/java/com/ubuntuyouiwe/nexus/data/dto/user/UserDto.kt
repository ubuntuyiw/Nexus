package com.ubuntuyouiwe.nexus.data.dto.user

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val uid: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean? = null,
    val phoneNumber: String? = null,
    val totalCompletionTokens: Double? = null,
    val totalPromptTokens: Double? = null,
    val totalTokens: Double? = null,
    val info: UserInfoDto? = null,
    val rateLimiting: Double? = null
)
