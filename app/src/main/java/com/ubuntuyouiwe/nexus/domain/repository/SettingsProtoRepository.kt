package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsProtoRepository {

    suspend fun updateSettings(updateAction: (SettingsDto.Builder) -> Unit)

    fun getSettings(): Flow<Settings>
}