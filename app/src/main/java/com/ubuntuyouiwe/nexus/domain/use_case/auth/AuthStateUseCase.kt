package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<User?>> = flow {
        emit(Resource.Loading)
        authRepository.listenUserOnlineStatus().catch {
            emit(Resource.Error(message = it.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }
    }

}