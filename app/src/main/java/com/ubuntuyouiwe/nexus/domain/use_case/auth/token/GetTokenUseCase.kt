package com.ubuntuyouiwe.nexus.domain.use_case.auth.token

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = flow<Resource<String>> {
    emit(Resource.Loading)
        try {
            val token = authRepository.getDeviceToken()
            emit(Resource.Success(token))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}