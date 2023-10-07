package com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_short

import com.google.protobuf.BoolValue
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomShortRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateChatRoomSortUseCase @Inject constructor(
    private val chatRoomShortRepository: ChatRoomShortRepository
) {
    operator fun invoke(chatRoomShort: ChatRoomShort): Flow<Resource<ChatRoomShort>> = flow {
        emit(Resource.Loading)
        chatRoomShortRepository.updateChatRoomShort { }
        try {
            val updatedData = chatRoomShortRepository.updateChatRoomShort {
                it.isNewestFirst = BoolValue.of(chatRoomShort.isNewestFirst)
            }
            emit(Resource.Success(updatedData))

        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }


    }
}