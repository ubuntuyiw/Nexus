package com.ubuntuyouiwe.nexus.data.dto.billing

import kotlinx.serialization.Serializable

@Serializable
data class AccountIdentifiersDto(
    val obfuscatedAccountId: String?,
    val obfuscatedProfileId: String?
)
