package com.ubuntuyouiwe.nexus.data.dto

import com.google.firebase.Timestamp
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto


data class ChatRoomDto(
    val id: String? = null,
    val name: String? = null,
    val ownerId: String? = null,
    val creationDate: Timestamp? = Timestamp.now(),
    @field:JvmField
    val isFavorited: Boolean? = null,
    @field:JvmField
    val isArchived: Boolean? = null,
    @field:JvmField
    val isPinned: Boolean? = null,
    val roleId: String? = null,
    val totalMessageCount: Double? = null,
    val lastMessage: String? = null,
    val lastMessageDate: Timestamp = Timestamp.now(),
    val role : RoleDto? = null
)