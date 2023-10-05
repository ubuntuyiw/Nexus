package com.ubuntuyouiwe.nexus.presentation.settings.theme

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.use_case.proto.settings.UpdateSettingsUseCase
import com.ubuntuyouiwe.nexus.presentation.main_activity.SettingsState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    sharedState: SharedState,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    ):ViewModel() {

    private val _settingsState = sharedState.settings
    val settingsState: State<SettingsState> = _settingsState
    fun onEvent(event: ThemeEvent) {
        when(event) {
            is ThemeEvent.ChangeTheme -> {
                updateSettings(settingsState.value.successData.copy(theme = event.themeOrdinal))
            }

        }
    }

    private fun updateSettings(settings: Settings) {
        updateSettingsUseCase(settings).onEach {

        }.launchIn(viewModelScope)

    }
}