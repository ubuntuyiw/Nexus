package com.ubuntuyouiwe.nexus.data.repository

import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSource
import com.ubuntuyouiwe.nexus.data.util.toRoles
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.repository.RoleRepository
import javax.inject.Inject

class RoleRepositoryImpl @Inject constructor(
    private val roleDatasource: RoleDataSource
): RoleRepository {
    override fun getRoles(): List<Role> {
        return roleDatasource.getRoles().map { it.toRoles() }
    }

    override fun getRole(id: String): Role {
        return roleDatasource.getRole(id).toRoles()
    }


}