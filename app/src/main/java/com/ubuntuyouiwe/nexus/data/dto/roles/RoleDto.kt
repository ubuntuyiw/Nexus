package com.ubuntuyouiwe.nexus.data.dto.roles

import kotlinx.serialization.Serializable

@Serializable
data class RoleDto(
    val name: RoleNameDto? = null,
    val description: Map<String, String>? = null,
    val image: String? = null,
    val type: String? = null,
    val system: String? = null
)
