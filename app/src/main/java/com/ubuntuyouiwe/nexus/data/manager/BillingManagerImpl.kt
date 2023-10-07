package com.ubuntuyouiwe.nexus.data.manager

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.firebase.functions.HttpsCallableResult
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.domain.util.ProductsFields
import com.ubuntuyouiwe.nexus.domain.util.toPurchaseDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BillingManagerImpl @Inject constructor(
    billingClient: BillingClient.Builder,
    private val firebaseDataSource: FirebaseDataSource,
    private val json: Json,
) : BillingManager {


    override var billingClientBuild: BillingClient

    init {
        billingClientBuild = billingClient
            .setListener(PurchasesUpdatedListener(::purchasesUpdated))
            .enablePendingPurchases()
            .build()
    }


    override val purchasesUpdateFlow =
        MutableSharedFlow<Pair<BillingResult, PurchaseDto?>>(
            extraBufferCapacity = 1,
            replay = 1
        )

    private fun purchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        purchasesUpdateFlow.tryEmit(billingResult to purchases?.map { it.toPurchaseDto(json) }
            ?.first())
    }

    override suspend fun handlePurchase(purchases: PurchaseDto): HttpsCallableResult? {
        val data = hashMapOf(
            "packageName" to purchases.packageName,
            "productId" to purchases.originalJson.productId,
            "purchaseToken" to purchases.purchaseToken
        )

        return firebaseDataSource.handlePurchase(data)
    }

    override val queryPurchases =
        MutableSharedFlow<Pair<BillingResult, List<PurchaseDto>>>(
            extraBufferCapacity = 1,
            replay = 1
        )

    override fun purchasesQuery() {
        billingClientBuild.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { queryPurchasesParams, purchasesResponse ->
            val purchaseDto = purchasesResponse.map { it.toPurchaseDto(json) }
            queryPurchases.tryEmit(queryPurchasesParams to purchaseDto)
        }
    }


    override val queryProductDetails =
        MutableSharedFlow<Pair<BillingResult, List<ProductDetails>>>(
            extraBufferCapacity = 1,
            replay = 1
        )

    override fun queryProductDetailsAsync(listOfProductIds: List<ProductsFields>) {
        val productList = listOfProductIds.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it.id)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClientBuild.queryProductDetailsAsync(queryProductDetailsParams) { queryBillingResult, productDetailsList ->
            queryProductDetails.tryEmit(queryBillingResult to productDetailsList)
        }
    }


    override val connectionResultFlow =
        MutableSharedFlow<Pair<BillingResult?, Boolean>>(
            extraBufferCapacity = 1,
            replay = 1
        )

    override fun endConnection() = billingClientBuild.endConnection()

    override fun isReady() = billingClientBuild.isReady

    override fun startConnection() {
        billingClientBuild.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(setupBillingResult: BillingResult) {
                connectionResultFlow.tryEmit(setupBillingResult to false)
            }

            override fun onBillingServiceDisconnected() {
                connectionResultFlow.tryEmit(null to true)
            }
        })
    }

}