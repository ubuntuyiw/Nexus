package com.ubuntuyouiwe.nexus.presentation.settings.main_settings

import androidx.lifecycle.ViewModel
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainSettingsViewModel @Inject constructor(
    sharedState: SharedState
):ViewModel() {
    val getPurposeSelection = sharedState.userState
}