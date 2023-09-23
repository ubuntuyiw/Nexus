package com.ubuntuyouiwe.nexus.data.dto.billing


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalJsonDto(
    @SerialName("packageName")
    var packageName: String?,
    @SerialName("productId")
    var productId: String?,
    @SerialName("purchaseState")
    var purchaseState: Int?,
    @SerialName("purchaseTime")
    var purchaseTime: Long?,
    @SerialName("purchaseToken")
    var purchaseToken: String?
)