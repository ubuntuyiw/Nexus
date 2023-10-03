package com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences

import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection

data class UpdatePurposeSelectionState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: PurposeSelection = PurposeSelection(),
)
