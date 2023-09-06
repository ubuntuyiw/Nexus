package com.ubuntuyouiwe.nexus.data.repository

import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSource
import com.ubuntuyouiwe.nexus.data.util.toSettings
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsProtoRepositoryImpl @Inject constructor(
    private val protoDataStoreDataSource: ProtoDataStoreDataSource
): SettingsProtoRepository {
    override suspend fun updateSettings(updateAction: (SettingsDto.Builder) -> Unit) {
        protoDataStoreDataSource.updateSettings(updateAction)
    }

    override fun getSettings(): Flow<Settings> = protoDataStoreDataSource.getSettings().map { it.toSettings() }

}