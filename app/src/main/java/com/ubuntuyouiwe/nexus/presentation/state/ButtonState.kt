package com.ubuntuyouiwe.nexus.presentation.state

import com.ubuntuyouiwe.nexus.R

data class ButtonState(
    var enabled: Boolean = true,
    var text: String = "",
    var icon: Int? = null
)
