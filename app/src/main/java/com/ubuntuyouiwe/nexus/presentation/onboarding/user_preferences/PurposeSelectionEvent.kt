package com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences

import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection

sealed interface PurposeSelectionEvent {
    data class UpdatePurposeSelection(val purposeSelection: PurposeSelection): PurposeSelectionEvent
}