package com.ubuntuyouiwe.nexus.presentation.state

import androidx.compose.ui.focus.FocusRequester

data class TextFieldState(
    var text: String = "",
    var isVisibility: Boolean = false,
    var isError: Boolean = false,
    var enabled: Boolean = true,
    var focusRequester: FocusRequester = FocusRequester()
)

