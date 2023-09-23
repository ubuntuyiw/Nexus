package com.ubuntuyouiwe.nexus.domain.util

import com.android.billingclient.api.Purchase
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.billing.AccountIdentifiersDto
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserCredentialsDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import kotlinx.serialization.json.Json


fun UserCredentials.toUserCredentialsDto(): UserCredentialsDto =
    UserCredentialsDto(
        email = this.email,
        password = this.password
    )


fun Messages.toMessagesDto(): MessagesDto =
    MessagesDto(
        messages = this.messages.map { it.toMessageItemDto() }
    )

fun MessageItem.toMessageItemDto(): MessageItemDto =
    MessageItemDto(
        content = this.content,
        role = this.role
    )

fun ChatRoom.toChatRoomDto(): ChatRoomDto =
    ChatRoomDto(
        id = this.id,
        isFavorited = this.isFavorited,
        isArchived = this.isArchived,
        isPinned = this.isPinned,
        roleId = this.roleId,
        name = this.name,
        totalMessageCount = this.totalMessageCount,
        role = this.role.toRolesDto()
    )

fun Role.toRolesDto(): RoleDto =
    RoleDto(
        type = this.type,
        system = this.system,

        )

fun Purchase.toPurchaseDto(json: Json): PurchaseDto =
    PurchaseDto(
        purchaseToken = this.purchaseToken,
        originalJson = json.decodeFromString(this.originalJson) ,
        products = this.products,
        packageName = this.packageName,
        orderId = this.purchaseToken,
        accountIdentifiers = AccountIdentifiersDto(this.accountIdentifiers?.obfuscatedAccountId, this.accountIdentifiers?.obfuscatedProfileId),
        developerPayload = this.developerPayload,
        isAcknowledged = this.isAcknowledged,
        isAutoRenewing = this.isAutoRenewing,
        purchaseState = this.purchaseState,
        purchaseTime = this.purchaseTime,
        quantity = this.quantity,
        signature = this.signature,
    )
