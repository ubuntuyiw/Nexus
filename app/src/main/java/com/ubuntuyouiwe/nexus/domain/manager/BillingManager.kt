package com.ubuntuyouiwe.nexus.domain.manager

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.google.firebase.functions.HttpsCallableResult
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.domain.util.ProductsFields
import kotlinx.coroutines.flow.MutableSharedFlow

interface BillingManager {

    val purchasesUpdateFlow: MutableSharedFlow<Pair<BillingResult,PurchaseDto?>>

    var billingClientBuild: BillingClient
    suspend fun handlePurchase(purchases: PurchaseDto): HttpsCallableResult?

    val queryPurchases: MutableSharedFlow<Pair<BillingResult, List<PurchaseDto>>>

    fun purchasesQuery()

    val queryProductDetails: MutableSharedFlow<Pair<BillingResult, List<ProductDetails>>>

    fun queryProductDetailsAsync(listOfProductIds: List<ProductsFields>)

    val connectionResultFlow: MutableSharedFlow<Pair<BillingResult?, Boolean>>

    fun startConnection()
    fun endConnection()

    fun isReady(): Boolean


}