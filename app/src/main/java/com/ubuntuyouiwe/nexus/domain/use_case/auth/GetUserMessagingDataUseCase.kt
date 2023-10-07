package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserMessagingDataUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(id: String) = flow<Resource<UserMessagingData>> {
        emit(Resource.Loading)
        try {
            authRepository.getUserMessagingData(id).collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}