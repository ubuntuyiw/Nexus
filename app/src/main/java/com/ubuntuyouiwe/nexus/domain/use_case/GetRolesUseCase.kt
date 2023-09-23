package com.ubuntuyouiwe.nexus.domain.use_case

import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.repository.RoleRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRolesUseCase @Inject constructor(
    private val roleRepository: RoleRepository
){
    operator fun invoke(): Flow<Resource<List<Role>>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(roleRepository.getRoles()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}