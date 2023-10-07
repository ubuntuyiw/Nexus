package com.ubuntuyouiwe.nexus.domain.use_case.firestore.delete_chat_rooms

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.worker.ConstantWorker.CHAT_ROOMS
import com.ubuntuyouiwe.nexus.domain.worker.DeleteChatRoomWorker
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChatRoomDeleteUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    operator fun invoke(chatRoom: List<ChatRoom>) = flow<Resource<UUID>> {
        emit(Resource.Loading)
        try {
            val workManager = WorkManager.getInstance(context)

            val chatRoomJson = json.encodeToString(chatRoom)
            val inputData = workDataOf(CHAT_ROOMS to chatRoomJson)


            val workRequest = OneTimeWorkRequestBuilder<DeleteChatRoomWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setInputData(inputData)
                .build()
            workManager.enqueue(workRequest)

            emit(Resource.Success(workRequest.id))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}