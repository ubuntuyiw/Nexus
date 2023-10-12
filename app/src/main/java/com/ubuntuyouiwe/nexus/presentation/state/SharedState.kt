package com.ubuntuyouiwe.nexus.presentation.state

import androidx.compose.runtime.mutableStateOf
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.image.Images
import com.ubuntuyouiwe.nexus.presentation.main_activity.GetTokenState
import com.ubuntuyouiwe.nexus.presentation.main_activity.SettingsState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserMessagingDataState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedState @Inject constructor() {
    val userState = mutableStateOf(UserOperationState())
    val settings = mutableStateOf(SettingsState())
    val userMessagingDataState = mutableStateOf(UserMessagingDataState())
    val token = mutableStateOf(GetTokenState())

}