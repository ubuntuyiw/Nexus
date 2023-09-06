package com.ubuntuyouiwe.nexus.domain.model.roles

import kotlinx.serialization.Serializable


@Serializable
data class Role(
    val name: RoleName = RoleName(),
    val description: Map<String, String> = mapOf(),
    val image: String = "",
    val type: String = "",
    val system: String = ""
)