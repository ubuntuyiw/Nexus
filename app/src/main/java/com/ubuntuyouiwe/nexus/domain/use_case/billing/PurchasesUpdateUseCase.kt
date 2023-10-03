package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PurchasesUpdateUseCase @Inject constructor(
    private val billingManager: BillingManager

) {
    operator fun invoke() = flow<Resource<Nothing>> {
        billingManager.purchasesUpdateFlow.collect { (billingResult, purchaseList) ->
            emit(Resource.Loading)
            if (billingResult.responseCode == BillingResponseCode.OK) {
                try {
                    purchaseList?.let {
                        billingManager.handlePurchase(purchaseList)
                    }
                    emit(Resource.Success())
                } catch (e: Exception) {
                    emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
                }
            } else {
                emit(Resource.Error(message = billingResult.debugMessage))
            }
        }
    }
}