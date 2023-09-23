package com.ubuntuyouiwe.nexus.presentation.state

import androidx.compose.runtime.mutableStateOf
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.image.Images
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserMessagingDataState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedState @Inject constructor() {
    val isDarkTheme = mutableStateOf(true)
    val userState = mutableStateOf(UserOperationState())
    val userMessagingDataState = mutableStateOf(UserMessagingDataState())

}