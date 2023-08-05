package com.ubuntuyouiwe.nexus.domain.util

import com.google.firebase.auth.FirebaseAuthException

class Preconditions {
    fun invalidEmail(email: String) {
        if (email.isBlank()) throw FirebaseAuthException(
            "ERROR_INVALID_EMAIL",
            "Please provide an email address."
        )
        if (email.isBlank()) throw FirebaseAuthException(
            "ERROR_INVALID_EMAIL",
            "Please provide an email address."
        )
    }

    fun invalidPassword(password: String) {
        if (password.isBlank()) throw FirebaseAuthException(
            "ERROR_WEAK_PASSWORD",
            "Please provide a password."
        )
        if (password.isBlank()) throw FirebaseAuthException(
            "ERROR_WEAK_PASSWORD",
            "Please provide a password."
        )
    }
}