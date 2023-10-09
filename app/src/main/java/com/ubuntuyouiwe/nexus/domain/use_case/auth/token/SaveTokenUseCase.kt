package com.ubuntuyouiwe.nexus.domain.use_case.auth.token

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: String) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            authRepository.saveTokenToDatabase(token)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error( message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}