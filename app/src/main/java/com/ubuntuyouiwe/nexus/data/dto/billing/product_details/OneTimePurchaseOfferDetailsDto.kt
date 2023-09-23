package com.ubuntuyouiwe.nexus.data.dto.billing.product_details

import kotlinx.serialization.Serializable

@Serializable
data class OneTimePurchaseOfferDetailsDto(
    val priceAmountMicros: Long?,
    val priceCurrencyCode: String?,
    val formattedPrice: String?
)
