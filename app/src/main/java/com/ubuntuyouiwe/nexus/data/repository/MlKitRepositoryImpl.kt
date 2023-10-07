package com.ubuntuyouiwe.nexus.data.repository

import com.google.mlkit.vision.common.InputImage
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.MlKit
import com.ubuntuyouiwe.nexus.domain.repository.MlKitRepository
import javax.inject.Inject

class MlKitRepositoryImpl @Inject constructor(
    private val mlKit: MlKit
) : MlKitRepository {

    override suspend fun getText(inputImage: InputImage): String? {
        return mlKit.imageToText(inputImage)?.text
    }
}