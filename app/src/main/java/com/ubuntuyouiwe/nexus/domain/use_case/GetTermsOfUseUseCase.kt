package com.ubuntuyouiwe.nexus.domain.use_case

import com.ubuntuyouiwe.nexus.domain.model.TermsOfUseModel
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import javax.inject.Inject

class GetTermsOfUseUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {
    operator fun invoke(): TermsOfUseModel = dataSyncRepository.getTermsOfUse()
}