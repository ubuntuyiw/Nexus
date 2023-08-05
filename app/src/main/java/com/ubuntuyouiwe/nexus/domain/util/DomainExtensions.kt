package com.ubuntuyouiwe.nexus.domain.util

import com.ubuntuyouiwe.nexus.data.dto.UserCredentialsDto
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials


fun UserCredentials.toUserCredentialsDto(): UserCredentialsDto =
    UserCredentialsDto(
        email = this.email,
        password = this.password
    )


