package com.ubuntuyouiwe.nexus.data.source.remote.firebase

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text

interface MlKit {
    suspend fun imageToText(image: InputImage): Text?
}