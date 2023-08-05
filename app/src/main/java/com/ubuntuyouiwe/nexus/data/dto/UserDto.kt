package com.ubuntuyouiwe.nexus.data.dto

import android.net.Uri

data class UserDto(
    val uid: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: Uri? = null,
    val isEmailVerified: Boolean? = null,
    val phoneNumber: String? = null,
)
