package com.ubuntuyouiwe.nexus.presentation.photo_editing

data class BitmapToStringState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val text: String = "",
    val errorText: String = ""
)