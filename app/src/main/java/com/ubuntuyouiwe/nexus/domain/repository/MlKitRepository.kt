package com.ubuntuyouiwe.nexus.domain.repository

import com.google.mlkit.vision.common.InputImage

interface MlKitRepository {
    suspend fun getText(inputImage: InputImage): String?
}