package com.ubuntuyouiwe.nexus.presentation.photo_editing


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun BoxScope.NewAreaArrangement(crop: (left: Float, top: Float, right: Float, bottom: Float, width: Float, height: Float) -> Unit) {
    var selectedCorner by remember { mutableStateOf<String?>("None") }
    var canvasWidth by remember { mutableIntStateOf(0) }
    var canvasHeight by remember { mutableIntStateOf(0) }

    var left by remember { mutableFloatStateOf(0f) }
    var top by remember { mutableFloatStateOf(0f) }
    var right by remember { mutableFloatStateOf(canvasWidth.toFloat()) }
    var bottom by remember { mutableFloatStateOf(canvasHeight.toFloat()) }

    crop(left, top, right, bottom, canvasWidth.toFloat(), canvasHeight.toFloat())

    val cornerLineArea = 100f

    LaunchedEffect(key1 = canvasWidth, key2 = canvasHeight) {
        right = canvasWidth.toFloat()
        bottom = canvasHeight.toFloat()
    }

    fun isTappableArea(
        corner: Offset,
        touch: Offset,
        area: Float = cornerLineArea
    ) = kotlin.math.abs(corner.x - touch.x) <= area && kotlin.math.abs(
        corner.y - touch.y
    ) <= area
    Canvas(
        modifier = Modifier
            .matchParentSize()
            .onGloballyPositioned { coordinates ->
                canvasWidth = coordinates.size.width
                canvasHeight = coordinates.size.height

            }
            .pointerInput(Unit) {
                detectDragGestures(

                    onDragStart = {
                        selectedCorner = when {
                            isTappableArea(
                                Offset(left, top),
                                it
                            ) && selectedCorner == null -> "TopLeft"

                            isTappableArea(
                                Offset(right, top),
                                it
                            ) && selectedCorner == null -> "TopRight"

                            isTappableArea(
                                Offset(left, bottom),
                                it
                            ) && selectedCorner == null -> "BottomLeft"

                            isTappableArea(
                                Offset(right, bottom),
                                it
                            ) && selectedCorner == null -> "BottomRight"

                            else -> null
                        }
                    },
                    onDragCancel = { selectedCorner = null },
                    onDragEnd = { selectedCorner = null },
                    onDrag = { change, dragAmount ->
                        when (selectedCorner) {
                            "TopLeft" -> {
                                if ((left + dragAmount.x) >= 0 && (left + dragAmount.x) < right - 150f) {
                                    if (right - left + dragAmount.x > 150f) left += dragAmount.x
                                    else if (dragAmount.x < 0f) left += dragAmount.x
                                }
                                if ((top + dragAmount.y) >= 0 && (top + dragAmount.y) < bottom - 150f) {
                                    if (bottom - top + dragAmount.y > 150f) top += dragAmount.y
                                    else if (dragAmount.y < 0f) top += dragAmount.y
                                }
                            }

                            "TopRight" -> {
                                if ((right + dragAmount.x) <= canvasWidth) {
                                    if (right - left + dragAmount.x > 150f) right += dragAmount.x
                                    else if (dragAmount.x > 0f) right += dragAmount.x
                                }
                                if ((top + dragAmount.y) >= 0 && (top + dragAmount.y) < bottom - 150f) {
                                    if (bottom - top + dragAmount.y > 150f) top += dragAmount.y
                                    else if (dragAmount.y < 0f) top += dragAmount.y
                                }
                            }

                            "BottomLeft" -> {
                                if ((left + dragAmount.x) >= 0 && (left + dragAmount.x) < right - 150f) {
                                    if (right - left + dragAmount.x > 150f) left += dragAmount.x
                                    else if (dragAmount.x < 0f) left += dragAmount.x
                                }
                                if ((bottom + dragAmount.y) <= canvasHeight) {
                                    if (bottom - top + dragAmount.y > 150f) bottom += dragAmount.y
                                    else if (dragAmount.y > 0f) bottom += dragAmount.y
                                }
                            }

                            "BottomRight" -> {
                                if ((right + dragAmount.x) <= canvasWidth) {
                                    if (right - left + dragAmount.x > 150f) right += dragAmount.x
                                    else if (dragAmount.x > 0f) right += dragAmount.x
                                }
                                if ((bottom + dragAmount.y) <= canvasHeight) {
                                    if (bottom - top + dragAmount.y > 150f) bottom += dragAmount.y
                                    else if (dragAmount.y > 0f) bottom += dragAmount.y
                                }
                            }

                            else -> {
                                if ((left + dragAmount.x) >= 0 && (left + dragAmount.x) < canvasWidth - 150f) {
                                    if (right - left + dragAmount.x > 150f) left += dragAmount.x
                                    else if (dragAmount.x < 0f) left += dragAmount.x
                                }
                                if ((top + dragAmount.y) >= 0 && (top + dragAmount.y) < canvasHeight - 150f) {
                                    if (bottom - top + dragAmount.y > 150f) top += dragAmount.y
                                    else if (dragAmount.y < 0f) top += dragAmount.y
                                }
                                if ((right + dragAmount.x) <= canvasWidth) {
                                    if (right - left + dragAmount.x > 150f) right += dragAmount.x
                                    else if (dragAmount.x > 0f) right += dragAmount.x
                                }
                                if ((bottom + dragAmount.y) <= canvasHeight) {
                                    if (bottom - top + dragAmount.y > 150f) bottom += dragAmount.y
                                    else if (dragAmount.y > 0f) bottom += dragAmount.y
                                }
                            }
                        }
                        change.consume()
                    }
                )
            }
    ) {
        val rects = listOf(
            Triple(Offset(0f, 0f), Size(canvasWidth.toFloat(), top), Color.Black.copy(alpha = 0.4f)),
            Triple(Offset(0f, bottom), Size(canvasWidth.toFloat(), canvasHeight.toFloat() - bottom), Color.Black.copy(alpha = 0.4f)),
            Triple(Offset(0f, top), Size(left, bottom - top), Color.Black.copy(alpha = 0.4f)),
            Triple(Offset(right, top), Size(canvasWidth.toFloat() - right, bottom - top), Color.Black.copy(alpha = 0.4f))
        )

        for ((topLeft, size, color) in rects) {
            drawRect(
                color = color,
                topLeft = topLeft,
                size = size
            )
        }
        drawRect(
            color = Color.Transparent,
            topLeft = Offset(x = left, y = top),
            size = Size(width = right - left, height = bottom - top)
        )

        val cellWidth = (right - left) / 3
        val cellHeight = (bottom - top) / 3

        for (i in 0..2) {
            for (j in 0..2) {
                val xStart = left + i * cellWidth
                val yStart = top + j * cellHeight
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset(x = xStart, y = yStart),
                    size = Size(width = cellWidth, height = cellHeight),
                    style = Stroke(width = 2f)
                )
            }
        }

        val borderLength = 75f
        val strokeWidth = 8f
        val corners = listOf(
            Pair(Offset(left, top), Offset(left + borderLength, top)),
            Pair(Offset(left, top), Offset(left, top + borderLength)),
            Pair(Offset(right, top), Offset(right - borderLength, top)),
            Pair(Offset(right, top), Offset(right, top + borderLength)),
            Pair(Offset(left, bottom), Offset(left + borderLength, bottom)),
            Pair(Offset(left, bottom), Offset(left, bottom - borderLength)),
            Pair(Offset(right, bottom), Offset(right - borderLength, bottom)),
            Pair(Offset(right, bottom), Offset(right, bottom - borderLength))
        )

        for ((start, end) in corners) {
            drawLine(
                color = White,
                start = start,
                end = end,
                strokeWidth = strokeWidth
            )
        }
    }
}