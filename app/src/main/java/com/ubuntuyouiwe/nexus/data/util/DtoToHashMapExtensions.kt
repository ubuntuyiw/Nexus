package com.ubuntuyouiwe.nexus.data.util

import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull


fun ChatRoomDto.firstToHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "id" to this.id,
        "name" to this.name,
        "ownerId" to this.ownerId,
        "creationDate" to this.creationDate,
        "isFavorited" to this.isFavorited,
        "isArchived" to this.isArchived,
        "isPinned" to this.isPinned,
        "roleId" to this.roleId,
        "totalMessageCount" to 1
    )

fun ChatRoomDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "isFavorited" to this.isFavorited,
        "isArchived" to this.isArchived,
        "isPinned" to this.isPinned,
        "totalMessageCount" to this.totalMessageCount
    )

fun MessagesDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "messages" to this.messages,
        "created" to this.created,
    )
fun MessageItemDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "content" to this.content,
        "role" to this.role
    )

fun UserDto.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "uid" to this.uid,
        "displayName" to this.displayName,
        "email" to this.email,
        "photoUrl" to this.photoUrl,
        "isEmailVerified" to this.isEmailVerified,
        "phoneNumber" to this.phoneNumber,
        "totalCompletionTokens" to this.totalCompletionTokens,
        "totalPromptTokens" to this.totalPromptTokens,
        "totalTokens" to this.totalTokens,
        "info" to this.info,
        "rateLimiting" to this.rateLimiting
    )


fun AIRequest.toHashMap(): HashMap<String, Any?> =
    hashMapOf(
        "aiRequestBody" to this.aiRequestBody,
        "chatRoomId" to this.chatRoomId,
        "info" to this.info,
        "messageId" to this.messageId,
        "ownerId" to this.ownerId
    )


fun JsonElement.toAny(): Any? = when (this) {
    is JsonObject -> entries.associate { it.key to it.value.toAny() }
    is JsonArray -> this.map { it.toAny() }
    is JsonPrimitive -> when {
        isString -> content
        doubleOrNull != null -> double
        intOrNull != null -> int
        booleanOrNull != null -> boolean
        else -> null
    }

    else -> null
}

