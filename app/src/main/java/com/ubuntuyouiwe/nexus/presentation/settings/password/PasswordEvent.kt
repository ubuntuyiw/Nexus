package com.ubuntuyouiwe.nexus.presentation.settings.password

sealed interface PasswordEvent {
    data object ChangePasswordVisibility: PasswordEvent
    data class ChangePasswordTextField(val text: String): PasswordEvent
    data class ChangePassword(val newPassword: String): PasswordEvent
}