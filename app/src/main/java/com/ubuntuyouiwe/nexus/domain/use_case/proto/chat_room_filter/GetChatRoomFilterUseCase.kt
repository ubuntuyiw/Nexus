package com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_filter

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomFilterRepository
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatRoomFilterUseCase @Inject constructor(
    private val chatRoomFilterRepository: ChatRoomFilterRepository
) {
    operator fun invoke(): Flow<Resource<ChatRoomFilter>> = flow {
        emit(Resource.Loading)
        chatRoomFilterRepository.getChatRoomFilter().catch {
            emit(Resource.Error(message = it.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }

    }
}