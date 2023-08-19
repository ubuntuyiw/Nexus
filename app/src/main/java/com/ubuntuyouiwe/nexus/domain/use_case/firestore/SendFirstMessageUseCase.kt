package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendFirstMessageUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {

    operator fun invoke(chatRoom: ChatRoom, messages: List<MessageItem>) = flow<Resource<Any>>{
        emit(Resource.Loading())
        try {
            dataSyncRepository.sendMessage(chatRoom, messages)
            emit(Resource.Success())
        }catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }

    }
}