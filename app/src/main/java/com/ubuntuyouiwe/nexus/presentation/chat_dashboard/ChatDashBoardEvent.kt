package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.MessagingPanelOnEvent
import java.util.UUID

sealed interface ChatDashBoardEvent {

    data object LogOut : ChatDashBoardEvent

    data object ChatRoomsRetry: ChatDashBoardEvent

    data class ChangeMenuVisibility(val isVisibility: Boolean): ChatDashBoardEvent

    data class ChangeFilterDialogVisibility(val isVisibility: Boolean): ChatDashBoardEvent

    data class ChatRoomShortChange(val chatRoomShort: ChatRoomShort): ChatDashBoardEvent

    data class ChatRoomFilterChange(val chatRoomFilter: ChatRoomFilter): ChatDashBoardEvent

    data class ChatRoomUpdate(val chatRooms: List<ChatRoom>): ChatDashBoardEvent

    data class ChatRoomDelete(val chatRooms: List<ChatRoom>): ChatDashBoardEvent

    data class ChatRoomCancelDelete(val id: UUID): ChatDashBoardEvent

}
