package com.ubuntuyouiwe.nexus.domain.model

import com.google.firebase.Timestamp
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoom(
    val id: String = "",
    val name: String = "New Chat",
    val isFavorited: Boolean = false,
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val roleId: String = "",
    val totalMessageCount: Double = 0.0,
    val role : Role = Role(),
    val lastMessage: String = "",
    val lastMessageDate: String = "",
    val isNew: Boolean = false
)
