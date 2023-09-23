package com.ubuntuyouiwe.nexus.data.dto.billing

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDto(
    val purchaseToken: String,
    val originalJson: OriginalJsonDto,
    val products: List<String>,
    val packageName: String,
    val orderId: String?,
    val accountIdentifiers: AccountIdentifiersDto?,
    val developerPayload: String,
    val isAcknowledged: Boolean,
    val isAutoRenewing: Boolean,
    val purchaseState: Int,
    val purchaseTime: Long,
    val quantity: Int?,
    val signature: String
)

