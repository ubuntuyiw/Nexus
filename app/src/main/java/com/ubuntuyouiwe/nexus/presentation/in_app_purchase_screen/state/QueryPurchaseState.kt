package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state

import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto


data class QueryPurchaseState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<PurchaseDto> = emptyList()
)
