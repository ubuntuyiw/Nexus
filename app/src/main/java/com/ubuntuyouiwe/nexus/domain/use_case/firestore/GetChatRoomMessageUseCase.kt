package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChatRoomMessageUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {

    operator fun invoke(id: String) = flow<Resource<Message?>> {
        emit(Resource.Loading)
        dataSyncRepository.getChatRoomMessage(id).catch {
            emit(Resource.Error(message = it.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }.collect {
            emit(Resource.Success(it))
        }

    }
}