package com.ubuntuyouiwe.nexus.presentation.photo_editing

import android.graphics.Bitmap
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoardEvent

sealed interface PhotoEditingEvent {
    data class ChangeDialogVisibility(val isVisibility: Boolean): PhotoEditingEvent

    data class ApplyCrop( val bitmap: Bitmap?) : PhotoEditingEvent

    data class ToMessagingPanel(val navController: NavController, val bitmap: Bitmap?) : PhotoEditingEvent





}