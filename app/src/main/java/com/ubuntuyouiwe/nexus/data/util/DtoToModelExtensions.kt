package com.ubuntuyouiwe.nexus.data.util

import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleNameDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.roles.RoleName
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState

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

fun ChatRoomDto.toChatRoom(): ChatRoom =
    ChatRoom(
        id = this.id?: "",
        name = this.name?: "",
        isFavorited = this.isFavorited?: false,
        isArchived = this.isArchived?: false,
        isPinned = this.isPinned?: false,
        roleId = this.roleId?: "",
        totalMessageCount = this.totalMessageCount?: 0.0,
        speechState = this.speechState?: SpeechState(),
        role = this.role?.toRoles()?: RoleDto().toRoles()
    )







