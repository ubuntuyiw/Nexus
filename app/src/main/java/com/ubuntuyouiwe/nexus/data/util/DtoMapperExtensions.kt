package com.ubuntuyouiwe.nexus.data.util

import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.data.dto.UserDto
import com.ubuntuyouiwe.nexus.domain.model.User

fun FirebaseUser.toUserDto(): UserDto =
    UserDto(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl,
        isEmailVerified = this.isEmailVerified,
        phoneNumber = this.phoneNumber

    )

fun UserDto.toUser(): User =
    User(
        email = this.email
    )
