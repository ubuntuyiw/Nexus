package com.ubuntuyouiwe.nexus.data.source.local

import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto

interface RoleDataSource {
    fun getRoles(): List<RoleDto>

    fun getRole(id: String): RoleDto
}