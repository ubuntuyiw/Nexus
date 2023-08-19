package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.domain.model.roles.Role

interface RoleRepository {
    fun getRoles(): List<Role>
    fun getRole(id: String): Role
}