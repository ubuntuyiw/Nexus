package com.ubuntuyouiwe.nexus.data.dto.billing.product_details

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsDto(
    val productId: String?,
    val name: String,
    val description: String,
    val oneTimePurchaseOfferDetails: OneTimePurchaseOfferDetailsDto
)
