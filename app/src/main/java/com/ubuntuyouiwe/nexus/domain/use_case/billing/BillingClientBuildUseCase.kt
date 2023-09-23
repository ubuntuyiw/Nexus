package com.ubuntuyouiwe.nexus.domain.use_case.billing

import com.android.billingclient.api.BillingClient
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import javax.inject.Inject

class BillingClientBuildUseCase @Inject constructor(
    private val billingManager: BillingManager
) {
    operator fun invoke(): BillingClient = billingManager.billingClientBuild
}