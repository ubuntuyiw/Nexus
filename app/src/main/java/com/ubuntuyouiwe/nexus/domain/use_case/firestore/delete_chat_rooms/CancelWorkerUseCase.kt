package com.ubuntuyouiwe.nexus.domain.use_case.firestore.delete_chat_rooms

import android.content.Context
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject

class CancelWorkerUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(workRequestId: UUID) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelWorkById(workRequestId)
    }
}