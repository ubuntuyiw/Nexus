package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConsumeTheProductUseCase @Inject constructor(
    private val billingManager: BillingManager
) {
    operator fun invoke(purchaseDto: PurchaseDto) = flow<Resource<Nothing>> {
        emit(Resource.Loading)
        try {
            billingManager.handlePurchase(purchaseDto)
            emit(Resource.Success())

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }

    }
}