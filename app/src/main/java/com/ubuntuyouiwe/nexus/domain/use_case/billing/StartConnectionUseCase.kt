package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import javax.inject.Inject

class StartConnectionUseCase @Inject constructor(
    private val billingManager: BillingManager

) {
    operator fun invoke() {
        billingManager.startConnection()
    }
}