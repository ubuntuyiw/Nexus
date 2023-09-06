package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.domain.model.image.Image
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.photo_editing.PhotoEditingEvent

sealed interface MessagingPanelOnEvent {

    data class EnterMessage(val message: String): MessagingPanelOnEvent

    data class SendMessage(val content: String): MessagingPanelOnEvent

    data class Speak(val content: Messages): MessagingPanelOnEvent
    data class NavigateUp(val navController: NavController): MessagingPanelOnEvent

    data object SetSpeechRate: MessagingPanelOnEvent



}