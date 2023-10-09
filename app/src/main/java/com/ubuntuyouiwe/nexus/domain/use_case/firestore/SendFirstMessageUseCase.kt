package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendFirstMessageUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {

    operator fun invoke(chatRoom: ChatRoom, messages: Message, newMessage: MessageItem, isUserMessagingData: Boolean) =
        flow<Resource<Any>> {
            emit(Resource.Loading)
            try {
                dataSyncRepository.sendMessage(
                    totalMessageCountUpdate(chatRoom),
                    aiMemory(messages, newMessage),
                    isUserMessagingData
                )

                emit(Resource.Success())
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
            }
        }

    private fun totalMessageCountUpdate(chatRoom: ChatRoom): ChatRoom {

        return chatRoom.copy(
            totalMessageCount = chatRoom.totalMessageCount + 1
        )
    }

    private fun aiMemory(messages: Message, newMessage: MessageItem): List<MessageItem> {
        var sumTokens = 0.0


        val filterMessage = messages.messages.takeWhile {
            sumTokens += it.totalTokens
            sumTokens <= 3000
        }


        val messagesReversed = filterMessage.flatMap {
            it.messages.reversed()
        }.reversed()

        return messagesReversed + listOf(newMessage)
    }
}