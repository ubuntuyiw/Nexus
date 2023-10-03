package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDisplayNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(name: String): Flow<Resource<Nothing>> = flow  {
        emit(Resource.Loading)
        try {
            authRepository.updateDisplayName(name)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}