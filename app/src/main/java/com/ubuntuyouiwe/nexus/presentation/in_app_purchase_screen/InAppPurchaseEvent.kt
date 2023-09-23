package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen

import com.android.billingclient.api.BillingClient
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto

sealed interface InAppPurchaseEvent {
    data class ConsumePurchase(val purchaseDto: PurchaseDto): InAppPurchaseEvent

    data class BillingClientBuild(val billingClientBuild: (BillingClient) -> Unit): InAppPurchaseEvent
    data object RetryConnection: InAppPurchaseEvent

}