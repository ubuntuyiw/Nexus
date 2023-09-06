package com.ubuntuyouiwe.nexus.data.util

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleNameDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.domain.model.roles.RoleName
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
        email = this.email
    )

fun RoleDto.toRoles(): Role =
    Role(
        name = this.name?.toRoleName()?: RoleName("",""),
        description = this.description?: mapOf(),
        image = this.image?: "",
        type = this.type?: "",
        system = this.system?: ""
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
        id = this.id?: "",
        name = this.name?: "",
        isFavorited = this.isFavorited?: false,
        isArchived = this.isArchived?: false,
        isPinned = this.isPinned?: false,
        roleId = this.roleId?: "",
        totalMessageCount = this.totalMessageCount?: 0.0,
        lastMessage = this.lastMessage?: "",
        lastMessageDate = formattedDate,
        role = this.role?.toRoles()?: RoleDto().toRoles()
    )

}
fun MessageDto.toMessage(): Message =
    Message(
        messages = this.messages.map { it.toMessages() },
        isFromCache = this.isFromCache
    )

fun MessagesDto.toMessages(): Messages =
    Messages(
        id = this.id?: "",
        messages = this.messages.map { it.toMessageItem() },
        created = this.created?: Timestamp.now(),
        totalTokens = this.totalTokens?: 0.0,
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









