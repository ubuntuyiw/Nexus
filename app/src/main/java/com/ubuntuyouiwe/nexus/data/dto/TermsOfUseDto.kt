package com.ubuntuyouiwe.nexus.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TermsOfUseDto(
    val title: LanguageDto? = null,
    val acceptance: LanguageDto? = null,
    val license: LanguageDto? = null,
    val limitations: LanguageDto? = null,
    val disclaimer: LanguageDto? = null,
    val contact: LanguageDto? = null,
)
