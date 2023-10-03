package com.ubuntuyouiwe.nexus.domain.use_case.auth

import android.media.MediaDrm.ErrorCodes
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSystemMessageUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(systemMessage: String) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            authRepository.updateSystemMessage(systemMessage)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: com.ubuntuyouiwe.nexus.util.erros.ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}