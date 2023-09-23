package com.ubuntuyouiwe.nexus.domain.model

data class User(
    val uid: String = "",
    val email: String = "",
    val shouldLogout: Boolean = false,
    val isFromCache: Boolean = false,
    val hasPendingWrites: Boolean = false
)