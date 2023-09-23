package com.ubuntuyouiwe.nexus.domain.use_case.auth

import com.google.firebase.auth.FirebaseAuthException
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.util.Preconditions
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preconditions: Preconditions
) {

    operator fun invoke(param: UserCredentials): Flow<Resource<Any>> = flow {
        emit(Resource.Loading)
        try {
            preconditions.invalidEmail(param.email)
            preconditions.invalidPassword(param.password)
            authRepository.signUp(param)
            emit(Resource.Success())
        } catch (e: FirebaseAuthException) {
            emit(Resource.Error(errorCode = e.errorCode, message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }



}