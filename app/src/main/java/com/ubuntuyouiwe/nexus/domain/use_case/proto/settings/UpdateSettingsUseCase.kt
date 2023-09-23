package com.ubuntuyouiwe.nexus.domain.use_case.proto.settings

import com.google.protobuf.FloatValue
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsProtoRepository: SettingsProtoRepository
) {
    operator fun invoke(rate: Float): Flow<Resource<Settings>> = flow {
        emit(Resource.Loading)

        settingsProtoRepository.updateSettings {
            it.setSpeechRate = FloatValue.of(rate)
        }

    }
}