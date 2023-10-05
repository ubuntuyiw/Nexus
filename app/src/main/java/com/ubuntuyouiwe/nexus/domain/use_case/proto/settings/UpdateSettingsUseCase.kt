package com.ubuntuyouiwe.nexus.domain.use_case.proto.settings

import android.util.Log
import com.google.protobuf.FloatValue
import com.google.protobuf.Int32Value
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsProtoRepository: SettingsProtoRepository
) {
    operator fun invoke(settings: Settings): Flow<Resource<Settings>> = flow {
        emit(Resource.Loading)
        Log.v("asdasd",settings.toString())

        settingsProtoRepository.updateSettings {
            it.setSpeechRate = FloatValue.of(settings.setSpeechRate)
            it.theme = Int32Value.of(settings.theme)

        }

    }
}