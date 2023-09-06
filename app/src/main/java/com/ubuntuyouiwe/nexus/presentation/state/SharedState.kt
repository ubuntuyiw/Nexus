package com.ubuntuyouiwe.nexus.presentation.state

import androidx.compose.runtime.mutableStateOf
import com.ubuntuyouiwe.nexus.domain.model.image.Images
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedState @Inject constructor() {
    val isDarkTheme = mutableStateOf(true)

}