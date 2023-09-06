package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter

import com.ubuntuyouiwe.nexus.domain.model.roles.Role

data class FilterState(
    val isFavorited: Boolean = false,
    val selectedRoles: List<Role> = emptyList(),
    val isDialogVisibility: Boolean = false
)
