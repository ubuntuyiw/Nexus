package com.ubuntuyouiwe.nexus.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.domain.worker.ConstantWorker.CHAT_ROOMS
import com.ubuntuyouiwe.nexus.domain.worker.ConstantWorker.DELETE_CHAT_ROOMS_ERROR
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.serialization.json.Json


@HiltWorker
class DeleteChatRoomWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dataSyncRepository: DataSyncRepository,
    private val json: Json
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val jsonList = inputData.getString(CHAT_ROOMS)!!
            val myList: List<ChatRoom> = json.decodeFromString<List<ChatRoom>>(jsonList)
            dataSyncRepository.deleteChatRoomDocuments(myList)
            Result.success()
        } catch (e: Exception) {
            val outputData = Data.Builder()
                .putString(DELETE_CHAT_ROOMS_ERROR, e.message ?: ErrorCodes.UNKNOWN_ERROR.name)
                .build()
            Result.failure(outputData)
        }


    }

}