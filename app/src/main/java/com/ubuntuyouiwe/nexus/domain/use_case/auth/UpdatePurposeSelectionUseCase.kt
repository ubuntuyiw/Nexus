package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePurposeSelectionUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(purposeSelection: PurposeSelection) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            authRepository.updatePurposeSelection(purposeSelection)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }

    }
}