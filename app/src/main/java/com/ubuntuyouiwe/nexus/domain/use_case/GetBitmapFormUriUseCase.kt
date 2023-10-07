package com.ubuntuyouiwe.nexus.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import javax.inject.Inject

class GetBitmapFormUriUseCase @Inject constructor() {
    operator fun invoke(context: Context, uri: Uri): Bitmap? {
        var resultBitmap: Bitmap? = null
        context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
            val decodedBitmap = BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
            val exif = ExifInterface(descriptor.fileDescriptor)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            if (decodedBitmap != null) {
                val rotationMatrix = Matrix()
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotationMatrix.setRotate(90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotationMatrix.setRotate(180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotationMatrix.setRotate(270f)
                }

                resultBitmap = Bitmap.createBitmap(
                    decodedBitmap,
                    0,
                    0,
                    decodedBitmap.width,
                    decodedBitmap.height,
                    rotationMatrix,
                    true
                )
            }
        }
        return resultBitmap
    }
}