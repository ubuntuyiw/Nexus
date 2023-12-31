package com.ubuntuyouiwe.nexus.presentation.photo_editing

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar

@Composable
fun PhotoEditingScreen(
    navController: NavController,
    croppedPhotoState: CroppedPhotoState,
    bitmapToStringState: BitmapToStringState,
    onEvent: (event: PhotoEditingEvent) -> Unit,
    bitmap: Bitmap?
) {
    val cancel = stringResource(id = R.string.cancel)
    val preview = stringResource(id = R.string.preview)
    val croppedImage = stringResource(id = R.string.cropped_image)
    val editCrop = stringResource(id = R.string.edit_crop)
    val applyCrop = stringResource(id = R.string.apply_crop)
    val crop = stringResource(id = R.string.crop)
    val cropInstruction = stringResource(id = R.string.crop_instruction)

    if (bitmap == null) navController.navigateUp()

    var cropLeft by remember { mutableFloatStateOf(0f) }
    var cropTop by remember { mutableFloatStateOf(0f) }
    var cropRight by remember { mutableFloatStateOf(0f) }
    var cropBottom by remember { mutableFloatStateOf(0f) }


    val originalWidth = bitmap!!.width
    val originalHeight = bitmap.height

    var croppedBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }


    val x = cropLeft.toInt()
    val y = cropTop.toInt()
    val newWidth = (originalWidth - cropLeft - (originalWidth - cropRight)).toInt()
    val newHeight = (originalHeight - cropTop - (originalHeight - cropBottom)).toInt()

    var cardSize by remember { mutableIntStateOf(0) }
    if (croppedPhotoState.isDialogVisibility) {
        Dialog(onDismissRequest = {
            onEvent(PhotoEditingEvent.ChangeDialogVisibility(false))
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)

                ) {
                    croppedBitmap?.asImageBitmap()?.let {
                        val ratioBitmap = it.width.toFloat() / it.height.toFloat()
                        val scale = when {
                            ratioBitmap > 1f -> ContentScale.FillWidth
                            else -> ContentScale.FillHeight
                        }

                        Text(
                            text = preview,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp)
                        )



                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier
                                .padding(24.dp)
                                .size(with(LocalDensity.current) { (cardSize*0.60).toInt().toDp() })



                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (bitmapToStringState.isLoading) {
                                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                                } else if (bitmapToStringState.isError) {
                                    Text(
                                        text = bitmapToStringState.errorText,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(16.dp),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                } else if (bitmapToStringState.isSuccess) {
                                    onEvent(
                                        PhotoEditingEvent.ToMessagingPanel(
                                            navController,
                                            croppedBitmap
                                        )
                                    )

                                } else {
                                    Image(
                                        bitmap = it,
                                        contentDescription = croppedImage,
                                        contentScale = scale,
                                        modifier = Modifier.matchParentSize()

                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxWidth()
                            .heightIn(75.dp)
                    ) {
                        Button(
                            onClick = {
                                onEvent(PhotoEditingEvent.ChangeDialogVisibility(false))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = editCrop, style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Button(
                            onClick = {
                                onEvent(PhotoEditingEvent.ApplyCrop(croppedBitmap))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = applyCrop, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }


        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
            cardSize = if (coordinates.size.width > coordinates.size.height)  coordinates.size.height else  coordinates.size.width

        },
        topBar = {
            PrimaryTopAppBar(
                title = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = crop)
                    }
                }
            )

        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(onClick = {
                        navController.navigateUp()
                    }) {
                        Text(text = cancel, style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.padding(24.dp))

                    Button(
                        onClick = {
                            croppedBitmap = bitmap.let {
                                Bitmap.createBitmap(it, x, y, newWidth, newHeight)
                            }
                            onEvent(PhotoEditingEvent.ChangeDialogVisibility(true))

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = preview,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

            }
        }

    ) { paddingValues ->


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .padding(24.dp)
                .weight(1f, false)) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "",
                )





                NewAreaArrangement() { left, top, right, bottom, width, height ->
                    val rotationWight = originalWidth.toFloat() / width
                    val rotationHeight = originalHeight.toFloat() / height
                    cropLeft = left * rotationWight
                    cropTop = top * rotationHeight
                    cropRight = right * rotationWight
                    cropBottom = bottom * rotationHeight
                }
            }

            Text(
                text = cropInstruction,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )


        }
    }

}