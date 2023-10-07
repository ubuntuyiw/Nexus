package com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_short

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomShortRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatRoomSortUseCase @Inject constructor(
    private val chatRoomShortRepository: ChatRoomShortRepository
) {
    operator fun invoke(): Flow<Resource<ChatRoomShort>> = flow {
        emit(Resource.Loading)
        chatRoomShortRepository.getChatRoomShort().catch {
            emit(Resource.Error(message = it.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }

    }
}