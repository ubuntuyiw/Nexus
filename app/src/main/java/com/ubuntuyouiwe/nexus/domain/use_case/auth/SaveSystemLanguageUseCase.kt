package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSystemLanguageUseCase @Inject constructor(
    private val authRepository: AuthRepository
)  {
    operator fun invoke(code: String) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            authRepository.setSystemLanguage(code)

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}