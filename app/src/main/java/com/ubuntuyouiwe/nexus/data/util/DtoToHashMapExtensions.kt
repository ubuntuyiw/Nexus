package com.ubuntuyouiwe.nexus.data.util

import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.AIRequestBody
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.util.dto_type.AIRequestBodyFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.AIRequestFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.ChatRoomFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.messages.MessageItemFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.messages.MessagesFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.user.UserDtoFields


fun ChatRoomDto.firstToHashMap(): HashMap<String, Any?> =
    hashMapOf(
        ChatRoomFields.ID.key to this.id,
        ChatRoomFields.NAME.key to this.name,
        ChatRoomFields.OWNER_ID.key to this.ownerId,
        ChatRoomFields.CREATION_DATE.key to this.creationDate,
        ChatRoomFields.IS_FAVORITED.key to this.isFavorited,
        ChatRoomFields.IS_ARCHIVED.key to this.isArchived,
        ChatRoomFields.IS_PINNED.key to this.isPinned,
        ChatRoomFields.ROLE_ID.key to this.roleId,
        ChatRoomFields.TOTAL_MESSAGE_COUNT.key to 1
    )

fun ChatRoomDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        ChatRoomFields.NAME.key to this.name,
        ChatRoomFields.IS_FAVORITED.key to this.isFavorited,
        ChatRoomFields.IS_ARCHIVED.key to this.isArchived,
        ChatRoomFields.IS_PINNED.key to this.isPinned,
        ChatRoomFields.TOTAL_MESSAGE_COUNT.key to this.totalMessageCount
    )

fun MessagesDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        MessagesFields.MESSAGES.key to this.messages,
        MessagesFields.CREATED.key to this.created,
        MessagesFields.UPDATE_TIMESTAMP.key to this.updateTimestamp
    )

fun UserDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        UserDtoFields.UID.key to this.uid,
        UserDtoFields.DISPLAY_NAME.key to this.displayName,
        UserDtoFields.EMAIL.key to this.email,
        UserDtoFields.PHOTO_URL.key to this.photoUrl,
        UserDtoFields.IS_EMAIL_VERIFIED.key to this.isEmailVerified,
        UserDtoFields.PHONE_NUMBER.key to this.phoneNumber,
        UserDtoFields.INFO.key to this.info,
        UserDtoFields.OWNER_ID.key to this.ownerId,
        UserDtoFields.ID.key to this.id,
        UserDtoFields.SHOULD_LOGOUT.key to this.shouldLogout,
    )


fun AIRequest.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        AIRequestFields.AI_REQUEST_BODY.key to this.aiRequestBody.toHashMap(),
        AIRequestFields.CHAT_ROOM_ID.key to this.chatRoomId,
        AIRequestFields.INFO.key to this.info.map { it.toHashMap() },
        AIRequestFields.MESSAGE_ID.key to this.messageId,
        AIRequestFields.OWNER_ID.key to this.ownerId
    )
fun AIRequestBody.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        AIRequestBodyFields.MODEL.key to this.model,
        AIRequestBodyFields.MESSAGES.key to this.messages.map { it.toHashMap() },
        AIRequestBodyFields.MAX_TOKENS.key to this.max_tokens
    )


fun MessageItemDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
       MessageItemFields.CONTENT.key to this.content,
        MessageItemFields.ROLE.key to this.role
    )


