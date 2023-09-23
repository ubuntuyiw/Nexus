package com.ubuntuyouiwe.nexus.domain.model.billing


data class PurchasesModel(
    var packageName: String = "",
    var productId: String = "",
    var purchaseState: Int = 0,
    var purchaseTime: Long = 0L,
    var purchaseToken: String = ""
)
