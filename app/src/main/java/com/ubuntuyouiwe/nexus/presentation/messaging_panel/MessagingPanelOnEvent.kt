package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.image.Image
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ChatRoomUpdateState
import com.ubuntuyouiwe.nexus.presentation.photo_editing.PhotoEditingEvent

sealed interface MessagingPanelOnEvent {

    data class EnterMessage(val message: String): MessagingPanelOnEvent

    data class RenameChange(val chatRoomUpdateState: ChatRoomUpdateState): MessagingPanelOnEvent

    data class SendMessage(val content: String): MessagingPanelOnEvent

    data class Speak(val content: Messages): MessagingPanelOnEvent
    data class NavigateUp(val navController: NavController): MessagingPanelOnEvent

    data object SetSpeechRate: MessagingPanelOnEvent

    data class ChatRoomUpdate(val chatRooms: List<ChatRoom>): MessagingPanelOnEvent

    data object ChangeSpeechListener: MessagingPanelOnEvent


}