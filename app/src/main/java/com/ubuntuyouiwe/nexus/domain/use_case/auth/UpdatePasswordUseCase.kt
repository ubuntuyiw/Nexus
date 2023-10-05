package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.util.Preconditions
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preconditions: Preconditions

) {
    operator fun invoke(newPassword: String) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            preconditions.invalidPassword(newPassword)
            authRepository.changePassword(newPassword)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}