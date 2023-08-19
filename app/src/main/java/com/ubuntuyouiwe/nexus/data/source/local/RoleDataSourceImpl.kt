package com.ubuntuyouiwe.nexus.data.source.local

import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import javax.inject.Inject

class RoleDataSourceImpl @Inject constructor(
    private val roles: List<RoleDto>
): RoleDataSource {
    override fun getRoles(): List<RoleDto> {
        return roles
    }

    override fun getRole(id: String): RoleDto {
        return roles.first { it.type == id }
    }
}