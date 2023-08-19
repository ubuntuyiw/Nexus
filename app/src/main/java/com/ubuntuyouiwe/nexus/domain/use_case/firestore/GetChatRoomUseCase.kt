package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {

    operator fun invoke(id: String) = flow<Resource<ChatRoom?>>{
        emit(Resource.Loading())
        dataSyncRepository.getChatRoom(id).catch {
            emit(Resource.Error(message = it.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }

    }
}