package com.ubuntuyouiwe.nexus.data.source.remote.firebase

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import com.ubuntuyouiwe.nexus.di.RecognizerDefault
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MlKitImpl @Inject constructor(
    @RecognizerDefault val recognizer: TextRecognizer
) : MlKit {

    override suspend fun imageToText(image: InputImage): Text? {
        return recognizer.process(image).await()
    }
}