package com.ubuntuyouiwe.nexus.domain.model

import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState

data class ChatRoom(
    val id: String = "",
    val name: String = "New Chat",
    val isFavorited: Boolean = false,
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val roleId: String = "",
    val totalMessageCount: Double = 0.0,
    val speechState: SpeechState = SpeechState(),
    val role : Role = Role(),
    val isNew: Boolean = false
)
