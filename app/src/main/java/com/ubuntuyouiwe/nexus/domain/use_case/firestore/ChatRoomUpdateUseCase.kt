package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRoomUpdateUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {
    operator fun invoke(chatRoom: List<ChatRoom>) = flow<Resource<Nothing>>{
        emit(Resource.Loading)
        try {
            dataSyncRepository.updateChatRoomDocuments(chatRoom)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))        }

    }
}