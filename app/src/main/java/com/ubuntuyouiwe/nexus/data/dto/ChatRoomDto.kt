package com.ubuntuyouiwe.nexus.data.dto

import com.google.firebase.Timestamp
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState


data class ChatRoomDto(
    val id: String? = null,
    val name: String? = null,
    val ownerId: String? = null,
    val creationDate: Timestamp? = Timestamp.now(),
    val isFavorited: Boolean? = null,
    val isArchived: Boolean? = null,
    val isPinned: Boolean? = null,
    val roleId: String? = null,
    val totalMessageCount: Double? = null,
    val speechState: SpeechState? = null,
    val role : RoleDto? = null
)