package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import android.content.Intent

data class GoogleSignInState(
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val intent: Intent? = null
)
