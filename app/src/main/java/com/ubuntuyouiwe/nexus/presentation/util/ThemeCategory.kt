package com.ubuntuyouiwe.nexus.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.ui.graphics.vector.ImageVector

enum class ThemeCategory(val field: String, val icon: ImageVector) {
    IS_DARK_THEME("Dark Mode", Icons.Default.DarkMode),
    IS_LIGHT_THEME("Light Mode", Icons.Default.LightMode),
    IS_SYSTEM_THEME("System", Icons.Default.SystemUpdate)
}