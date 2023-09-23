package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.android.billingclient.api.BillingClient
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.util.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QueryPurchasesUseCase @Inject constructor(
    private val billingManager: BillingManager
) {
    operator fun invoke() = flow<Resource<List<PurchaseDto>>> {
        billingManager.queryPurchases.collect { (billingResult, purchaseDtoList) ->
            emit(Resource.Loading)
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                emit(Resource.Success( purchaseDtoList))
            } else {
                emit(Resource.Error( message = billingResult.debugMessage))
            }

        }
    }
}