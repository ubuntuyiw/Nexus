package com.ubuntuyouiwe.nexus.domain.model

import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection

data class User(
    val uid: String = "",
    val email: String = "",
    val shouldLogout: Boolean = false,
    val isOnBoarding: Boolean = false,
    val isFromCache: Boolean = false,
    val name: String = "",
    val hasPendingWrites: Boolean = false,
    val purposeSelection: PurposeSelection = PurposeSelection(),
    val systemMessage: String = ""
)