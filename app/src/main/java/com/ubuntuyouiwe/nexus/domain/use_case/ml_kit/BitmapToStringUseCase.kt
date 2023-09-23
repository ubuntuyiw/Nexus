package com.ubuntuyouiwe.nexus.domain.use_case.ml_kit

import com.google.mlkit.vision.common.InputImage
import com.ubuntuyouiwe.nexus.domain.repository.MlKitRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BitmapToStringUseCase @Inject constructor(
    private val mlKitRepository: MlKitRepository
) {
    operator fun invoke(inputImage: InputImage): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        try {
            val text = mlKitRepository.getText(inputImage) ?: throw Exception(ErrorCodes.UNKNOWN_ERROR.name)
            if (text.isEmpty()) throw Exception("No text was found in the image.")
            emit(Resource.Success(text))

        } catch (e: Exception) {
            emit(Resource.Error(errorCode = null, e.message?: ErrorCodes.UNKNOWN_ERROR.name))
        }
    }
}