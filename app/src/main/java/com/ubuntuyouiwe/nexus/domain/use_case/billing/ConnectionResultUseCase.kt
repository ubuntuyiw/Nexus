package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.android.billingclient.api.BillingClient
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.domain.util.ProductsFields
import com.ubuntuyouiwe.nexus.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectionResultUseCase @Inject constructor(
    private val billingManager: BillingManager
) {
    operator fun invoke() = flow<Resource<Nothing>> {
        billingManager.connectionResultFlow.collect { (billingResult, isConnected) ->
            emit(Resource.Loading)
            if (isConnected || billingResult == null) {
                emit(Resource.Error(message = "ServiceDisconnected"))
            } else {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val listOfProductIds = ProductsFields.values().toList()
                    billingManager.queryProductDetailsAsync(listOfProductIds)
                    emit(Resource.Success())

                } else {
                    emit(Resource.Error(message = billingResult.debugMessage))
                }
            }
        }
    }
}
