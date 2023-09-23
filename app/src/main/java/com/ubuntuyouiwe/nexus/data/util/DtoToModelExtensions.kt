package com.ubuntuyouiwe.nexus.data.util

import com.android.billingclient.api.ProductDetails
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.billing.OriginalJsonDto
import com.ubuntuyouiwe.nexus.data.dto.billing.product_details.OneTimePurchaseOfferDetailsDto
import com.ubuntuyouiwe.nexus.data.dto.billing.product_details.ProductDetailsDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleNameDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.dto.user_messaging_data.UserMessagingDataDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.model.billing.PurchasesModel
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.model.roles.RoleName
import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun FirebaseUser.toUserDto(): UserDto =
    UserDto(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl.toString(),
        isEmailVerified = this.isEmailVerified,
        phoneNumber = this.phoneNumber

    )

fun UserDto.toUser(): User =
    User(
        uid = this.uid ?: "",
        email = this.email ?: "",
        shouldLogout = this.shouldLogout ?: false,
        isFromCache = this.isFromCache?: false,
        hasPendingWrites = this.hasPendingWrites?: false
    )
fun UserMessagingDataDto.toUserMessagingData(): UserMessagingData =
    UserMessagingData(
        isFromCache = this.isFromCache?: false,
        hasPendingWrites = this.hasPendingWrites?: false,
        totalMessages = this.totalMessages?.toInt()?: 0
    )

fun RoleDto.toRoles(): Role =
    Role(
        name = this.name?.toRoleName() ?: RoleName("", ""),
        description = this.description ?: mapOf(),
        image = this.image ?: "",
        type = this.type ?: "",
        system = this.system ?: ""
    )

fun RoleNameDto.toRoleName(): RoleName =
    RoleName(
        TR = this.TR,
        EN = this.EN,
    )

fun ChatRoomDto.toChatRoom(): ChatRoom {
    val lastMessageDate: Date = this.lastMessageDate.toDate()
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedDate = formatter.format(lastMessageDate)

    return ChatRoom(
        id = this.id ?: "",
        name = this.name ?: "",
        isFavorited = this.isFavorited ?: false,
        isArchived = this.isArchived ?: false,
        isPinned = this.isPinned ?: false,
        roleId = this.roleId ?: "",
        totalMessageCount = this.totalMessageCount ?: 0.0,
        lastMessage = this.lastMessage ?: "",
        lastMessageDate = formattedDate,
        role = this.role?.toRoles() ?: RoleDto().toRoles()
    )

}

fun MessageDto.toMessage(): Message =
    Message(
        messages = this.messages.map { it.toMessages() },
        isFromCache = this.isFromCache
    )

fun MessagesDto.toMessages(): Messages =
    Messages(
        id = this.id ?: "",
        messages = this.messages.map { it.toMessageItem() },
        created = this.created ?: Timestamp.now(),
        totalTokens = this.totalTokens ?: 0.0,
        hasPendingWrites = this.hasPendingWrites,

        )

fun MessageItemDto.toMessageItem(): MessageItem =
    MessageItem(
        content = this.content,
        role = this.role,

        )

fun SettingsDto.toSettings(): com.ubuntuyouiwe.nexus.domain.model.Settings =
    com.ubuntuyouiwe.nexus.domain.model.Settings(
        setSpeechRate = this.setSpeechRate.value
    )

fun ChatRoomFilterDto.toChatRoomFilter(): ChatRoomFilter =
    ChatRoomFilter(
        isAllRoles = this.isAllRoles.value,
        isNeutralMode = this.isNeutralMode.value,
        isDebateArena = this.isDebateArena.value,
        isTravelAdvisor = this.isTravelAdvisor.value,
        isAstrologer = this.isAstrologer.value,
        isChef = this.isChef.value,
        isLawyer = this.isLawyer.value,
        isDoctor = this.isDoctor.value,
        isIslamicScholar = this.isIslamicScholar.value,
        isBiologyTeacher = this.isBiologyTeacher.value,
        isChemistryTeacher = this.isChemistryTeacher.value,
        isGeographyTeacher = this.isGeographyTeacher.value,
        isHistoryTeacher = this.isHistoryTeacher.value,
        isMathematicsTeacher = this.isMathematicsTeacher.value,
        isPhysicsTeacher = this.isPhysicsTeacher.value,
        isPsychologist = this.isPsychologist.value,
        isBishop = this.isBishop.value,
        isEnglishTeacher = this.isEnglishTeacher.value,
        isRelationshipCoach = this.isRelationshipCoach.value,
        isVeterinarian = this.isVeterinarian.value,
        isSoftwareDeveloper = this.isSoftwareDeveloper.value,
        isFavorited = this.isFavorited.value,
        isArchived = this.isArchived.value,
    )


fun ChatRoomShortDto.toChatRoomShort(): ChatRoomShort =
    ChatRoomShort(
        isNewestFirst = this.isNewestFirst.value,
    )

fun OriginalJsonDto.toPurchases() =
    PurchasesModel(
        packageName = this.packageName ?: "",
        productId = this.productId ?: "",
        purchaseToken = this.purchaseToken ?: "",
        purchaseState = this.purchaseState ?: 0,
        purchaseTime = this.purchaseTime ?: 0,
    )



fun ProductDetails.toProductDetailsDto(): ProductDetailsDto =
    ProductDetailsDto(
        productId = this.productId,
        name = this.name,
        description = this.description,
        oneTimePurchaseOfferDetails = OneTimePurchaseOfferDetailsDto(
            priceAmountMicros = this.oneTimePurchaseOfferDetails?.priceAmountMicros,
            priceCurrencyCode = this.oneTimePurchaseOfferDetails?.priceCurrencyCode,
            formattedPrice = this.oneTimePurchaseOfferDetails?.formattedPrice
        )
    )









