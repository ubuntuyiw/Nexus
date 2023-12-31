package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoardEvent
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.Chapter
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageArea(
    messages: Messages,
    index: Int,
    textToSpeech: TextToSpeech,
    hostState: SnackbarHostState,
    expendedMessage: () -> Unit,
    speechStateChange: (Messages) -> Unit,
) {
    val seeMore = stringResource(id = R.string.see_more)
    val seeLess = stringResource(id = R.string.see_less)
    val selectText = stringResource(id = R.string.select_text)
    val messageSending = stringResource(id = R.string.message_sending)
    val aiWriting = stringResource(id = R.string.ai_writing)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val settings = stringResource(id = R.string.settings)
    val textToSpeechSettings = stringResource(id = R.string.textToSpeechSettings)

    var dropdownMenuState by remember {
        mutableStateOf(false)
    }
    val maxText = 150

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { },
                onLongClick = {
                    dropdownMenuState = true
                }
            )
    ) {
        Chapter(
            top = {
                if (messages.messages.isNotEmpty()) {
                    val message =
                        if (messages.messages[0].content.length > maxText && !messages.messages[0].isExpanded && index != 0 )
                            buildAnnotatedString {
                                pushStringAnnotation(
                                    tag = "See",
                                    annotation = ""
                                )
                                append(messages.messages[0].content.subSequence(0, maxText))
                                pop()
                                append("...")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                        fontSize = 13.sp,
                                    )
                                ) {
                                    pushStringAnnotation(
                                        tag = "See",
                                        annotation = "SeeMore"
                                    )
                                    append(" $seeMore")
                                    pop()
                                }

                            } else
                            buildAnnotatedString {
                                pushStringAnnotation(
                                    tag = "See",
                                    annotation = ""
                                )
                                append(messages.messages[0].content)
                                pop()

                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                        fontSize = 13.sp,
                                    )
                                ) {
                                    pushStringAnnotation(
                                        tag = "See",
                                        annotation = "SeeLess"
                                    )
                                    append(if (messages.messages[0].content.length > maxText  && index != 0) " $seeLess " else "")
                                    pop()
                                }

                            }

                    ClickableText(
                        text = message,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 13.sp,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        modifier = Modifier
                            .weight(1f, false)
                            .animateContentSize(animationSpec = tween(1000)),
                        onClick = { offset ->
                            message.getStringAnnotations(
                                tag = "See",
                                start = offset, end = offset
                            ).firstOrNull()?.let { annotation ->

                                speechStateChange(
                                    messages.copy(
                                        messages = messages.messages.map {
                                            if (it.role == "user") {
                                                it.copy(
                                                    isExpanded = when (annotation.item) {
                                                        "SeeMore" -> {
                                                            true
                                                        }

                                                        "SeeLess" -> {
                                                            false
                                                        }

                                                        else -> {
                                                            !messages.messages[0].isExpanded
                                                        }
                                                    }
                                                )

                                            }
                                            else it
                                        }
                                    )
                                )



                            }
                        }
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    IconButton(
                        onClick = {
                            if (textToSpeech.defaultEngine != "com.google.android.tts") {
                                scope.launch {
                                    val result = hostState.showSnackbar(
                                        textToSpeechSettings,
                                        duration = SnackbarDuration.Indefinite,
                                        actionLabel = settings,
                                        withDismissAction = true,
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        val intent = Intent()
                                        intent.action = "com.android.settings.TTS_SETTINGS"
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        context.startActivity(intent)
                                    }
                                }
                            } else {
                                speechStateChange(
                                    messages.copy(isSpeak = !messages.isSpeak,)
                                )
                            }


                        },

                        modifier = Modifier
                            .clip(CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (messages.isSpeak) {
                                Icons.Default.Pause
                            } else Icons.Default.PlayArrow,
                            contentDescription = Icons.Default.PlayArrow.name,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { dropdownMenuState = true },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = Icons.Default.MoreVert.name,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )

                    }



                    DropdownMenu(
                        expanded = dropdownMenuState,
                        onDismissRequest = { dropdownMenuState = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.scrim)
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.TextSnippet,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = Icons.Default.TextSnippet.name
                                )
                            },
                            text = {
                                Text(
                                    text = selectText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }, onClick = {
                                dropdownMenuState = false
                                expendedMessage()
                            })

                    }

                }


            },
            bottom = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                    if (messages.messages.size > 1) {

                        val message =
                            if (messages.messages[1].content.length > maxText && !messages.messages[1].isExpanded && index != 0 )
                                buildAnnotatedString {
                                    pushStringAnnotation(
                                        tag = "See",
                                        annotation = ""
                                    )
                                    append(messages.messages[1].content.subSequence(0, maxText))
                                    pop()
                                    append("...")
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontSize = 13.sp,
                                        )
                                    ) {
                                        pushStringAnnotation(
                                            tag = "See",
                                            annotation = "SeeMore"
                                        )
                                        append(" $seeMore")
                                        pop()
                                    }

                                } else
                                buildAnnotatedString {
                                    pushStringAnnotation(
                                        tag = "See",
                                        annotation = ""
                                    )
                                    append(messages.messages[1].content)
                                    pop()

                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.inversePrimary,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontSize = 13.sp,
                                        )
                                    ) {
                                        pushStringAnnotation(
                                            tag = "See",
                                            annotation = "SeeLess"
                                        )
                                        append(if (messages.messages[1].content.length > maxText && index != 0) " $seeLess " else "")
                                        pop()
                                    }

                                }
                        ClickableText(
                            text = message,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontSize = 13.sp,
                                lineHeight = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            modifier = Modifier .animateContentSize(animationSpec = tween(1000)),
                            onClick = { offset ->
                                message.getStringAnnotations(
                                    tag = "See",
                                    start = offset, end = offset
                                ).firstOrNull()?.let { annotation ->

                                    speechStateChange(
                                        messages.copy(
                                            messages = messages.messages.map {
                                                if (it.role == "assistant") {
                                                    it.copy(
                                                        isExpanded = when (annotation.item) {
                                                            "SeeMore" -> {
                                                                true
                                                            }

                                                            "SeeLess" -> {
                                                                false
                                                            }

                                                            else -> {
                                                                !messages.messages[1].isExpanded
                                                            }
                                                        }
                                                    )

                                                }
                                                else it
                                            }
                                        )
                                    )

                                }
                            },
                        )
                    }
                }

                AnimatedVisibility(visible = messages.messages.size <= 1) {
                    if (messages.hasPendingWrites) Text(
                        text = messageSending,
                        style = MaterialTheme.typography.labelMedium
                    )
                    AnimatedVisibility(visible = !messages.hasPendingWrites) {
                        Text(
                            text = aiWriting,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                }
            }
        )

    }


}
