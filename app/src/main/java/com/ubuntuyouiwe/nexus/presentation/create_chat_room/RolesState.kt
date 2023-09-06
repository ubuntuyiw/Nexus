package com.ubuntuyouiwe.nexus.presentation.create_chat_room

import com.ubuntuyouiwe.nexus.domain.model.roles.Role

data class RolesState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: List<Role> = emptyList(),
)
