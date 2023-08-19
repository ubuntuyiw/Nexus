package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.LightGray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageArea(
    messages: Messages,
    index: Int,
    speechState: SpeechState,
    onclick: (SpeechState) -> Unit,
) {

    var expandedState by remember {
        mutableStateOf(false)
    }
    var isLongText by remember { mutableStateOf(true) }
    var lineCount by remember { mutableIntStateOf(0) }


    Card(
        onClick = {
            expandedState = !expandedState
        },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .animateContentSize()

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = LightGray
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (messages.messages.isNotEmpty()) {
                        Text(
                            text = messages.messages[0].content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .weight(90f)
                        )
                    }

                    PrimaryIconButton(
                        onClick = {
                            onclick(
                                SpeechState(
                                    isSpeak = (speechState.isSpeak && speechState.index == index),
                                    content = if (messages.messages.size > 1) messages.messages[1].content else "Please Waiting...",
                                    codeLanguage = if (messages.messages.size > 1) "TR" else "EN",
                                    index = index
                                )
                            )


                        },
                        modifier = Modifier.weight(10f)
                    ) {
                        Icon(
                            imageVector = if (speechState.isSpeak && speechState.index == index) ImageVector.vectorResource(
                                id = R.drawable.baseline_pause_24
                            ) else Icons.Default.PlayArrow,
                            contentDescription = Icons.Default.PlayArrow.name
                        )
                    }
                }

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                if (messages.messages.size > 1) {
                    if (lineCount > 0) isLongText = lineCount > 1
                    if ((expandedState || index == 0) && isLongText) {
                        Text(
                            text = messages.messages[1].content,
                            style = MaterialTheme.typography.bodyMedium,
                            onTextLayout = { layoutResult ->
                                lineCount = layoutResult.lineCount
                            }
                        )
                    } else {
                        Text(
                            text = messages.messages[1].content,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,

                            )

                        Text(
                            text = "See All",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                }
                AnimatedVisibility(visible = messages.messages.size <= 1) {
                    if (messages.hasPendingWrites) Text(text = "Your message is being sent...", style = MaterialTheme.typography.labelMedium)
                    AnimatedVisibility(visible = !messages.hasPendingWrites) {
                        Text(text = "Artificial Intelligence is writing...", style = MaterialTheme.typography.labelMedium)
                    }
                    
                }
            }

        }

    }

}
