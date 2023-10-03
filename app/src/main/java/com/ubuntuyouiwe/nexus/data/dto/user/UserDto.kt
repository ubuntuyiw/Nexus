package com.ubuntuyouiwe.nexus.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val uid: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    @field:JvmField
    val isEmailVerified: Boolean? = null,
    val phoneNumber: String? = null,
    val info: UserInfoDto? = null,
    val ownerId: String? = null,
    val id: String? = null,
    @field:JvmField
    val isOnBoarding: Boolean? = null,
    @field:JvmField
    val shouldLogout: Boolean? = null,
    val isFromCache: Boolean? = null,
    val hasPendingWrites: Boolean? = null,
    val purposeSelection: PurposeSelectionDto? = null,
    val systemMessage: String? = null

)
