package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: String): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        try {
            authRepository.removeTokenFromDatabase(token)
            authRepository.logOut()
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }

}