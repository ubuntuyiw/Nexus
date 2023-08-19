package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState

sealed interface MessagingPanelOnEvent {

    data class EnterMessage(val message: String): MessagingPanelOnEvent

    data class SendMessage(val content: String): MessagingPanelOnEvent

    data class Speak(val content: SpeechState): MessagingPanelOnEvent
    data class NavigateUp(val navController: NavController): MessagingPanelOnEvent

}