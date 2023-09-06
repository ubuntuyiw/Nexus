package com.ubuntuyouiwe.nexus.domain.use_case.proto.settings


import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsProtoRepository: SettingsProtoRepository
) {
    operator fun invoke(): Flow<Resource<Settings>> = flow {
        emit(Resource.Loading())
        settingsProtoRepository.getSettings().catch {
            emit(Resource.Error(message = it.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }

    }
}