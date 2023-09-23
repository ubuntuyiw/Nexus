package com.ubuntuyouiwe.nexus.domain.use_case.auth

import android.content.Intent
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(data: Intent): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        try {
            val result = authRepository.googleSignIn(data)
            authRepository.loginUserDatabase(result?.uid)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}