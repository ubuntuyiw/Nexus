package com.ubuntuyouiwe.nexus.domain.model

import com.ubuntuyouiwe.nexus.data.dto.LanguageDto

data class TermsOfUseModel(
    val title: LanguageModel = LanguageModel(),
    val acceptance: LanguageModel = LanguageModel(),
    val license: LanguageModel = LanguageModel(),
    val limitations: LanguageModel = LanguageModel(),
    val disclaimer: LanguageModel = LanguageModel(),
    val contact: LanguageModel = LanguageModel(),
)
