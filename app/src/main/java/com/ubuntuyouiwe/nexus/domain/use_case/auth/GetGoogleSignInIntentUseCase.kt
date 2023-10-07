package com.ubuntuyouiwe.nexus.domain.use_case.auth

import android.content.Intent
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import javax.inject.Inject

class GetGoogleSignInIntentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Intent {
        return authRepository.googleSignInIntent()
    }
}