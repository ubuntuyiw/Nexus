package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.ProductDetails
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueryProductDetailsUseCase @Inject constructor(
    private val billingManager: BillingManager
) {
    operator fun invoke() = flow<Resource<List<ProductDetails>>> {
        billingManager.queryProductDetails.collect { (billingResult, productDetailList) ->
            emit(Resource.Loading)
            if (billingResult.responseCode == BillingResponseCode.OK) {
                emit(Resource.Success(productDetailList))
            } else {
                emit(Resource.Error(message = billingResult.debugMessage))
            }
        }
    }
}