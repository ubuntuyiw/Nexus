package com.ubuntuyouiwe.nexus.presentation.photo_editing

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.ubuntuyouiwe.nexus.domain.model.image.Image
import com.ubuntuyouiwe.nexus.domain.model.image.Images
import com.ubuntuyouiwe.nexus.domain.use_case.GetBitmapFormUriUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.ml_kit.BitmapToStringUseCase
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoEditingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bitmapToStringUseCase: BitmapToStringUseCase,
    private val application: Application,
    private val getBitmapFormUriUseCase: GetBitmapFormUriUseCase,
) : AndroidViewModel(application) {


    var image by mutableStateOf<InputImage?>(null)

    var bitmap by mutableStateOf<Bitmap?>(null)
    var resultText by mutableStateOf("")

    private val _croppedPhotoState = mutableStateOf(CroppedPhotoState())
    val croppedPhotoState: State<CroppedPhotoState> = _croppedPhotoState


    private val _bitmapToStringState = mutableStateOf(BitmapToStringState())
    val bitmapToStringState: State<BitmapToStringState> = _bitmapToStringState



    init {
        val uriDecode = Uri.decode(savedStateHandle.get<String>("photoUrl")).toUri()

        bitmap = getBitmapFromUri(application.applicationContext, uriDecode)

        bitmap?.let {
            image = InputImage.fromBitmap(it, 0)
        }
    }

    fun onEvent(event: PhotoEditingEvent) {
        when (event) {
            is PhotoEditingEvent.ChangeDialogVisibility -> {
                _bitmapToStringState.value = BitmapToStringState()
                _croppedPhotoState.value =
                    croppedPhotoState.value.copy(isDialogVisibility = event.isVisibility)
            }
            is PhotoEditingEvent.ApplyCrop -> {
                event.bitmap?.let {
                    bitmapToString(it)
                }
            }
            is PhotoEditingEvent.ToMessagingPanel -> {
                event.bitmap?.let {
                    _croppedPhotoState.value =
                        croppedPhotoState.value.copy(isDialogVisibility = false)
                    event.navController.previousBackStackEntry?.savedStateHandle?.set("image_text", bitmapToStringState.value.text)
                    event.navController.navigateUp()
                }


            }

        }
    }

   private fun bitmapToString(bitmap: Bitmap) {

       val inputImage = InputImage.fromBitmap(bitmap,0)
        bitmapToStringUseCase(inputImage).onEach {
            when (it) {
                is Resource.Loading -> {
                    _bitmapToStringState.value = bitmapToStringState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _bitmapToStringState.value = bitmapToStringState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        text = it.data!!
                    )

                }

                is Resource.Error -> {
                    _bitmapToStringState.value = bitmapToStringState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorText = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return getBitmapFormUriUseCase(context, uri)
    }

}