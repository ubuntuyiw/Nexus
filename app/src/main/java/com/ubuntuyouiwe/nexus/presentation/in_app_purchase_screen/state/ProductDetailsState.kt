package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state

import com.android.billingclient.api.ProductDetails
import com.ubuntuyouiwe.nexus.data.dto.billing.product_details.ProductDetailsDto

data class ProductDetailsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val data: List<ProductDetails> = emptyList(),
    val errorMessage: String = ""
)
