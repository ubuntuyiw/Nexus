package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.Chapter


@Composable
fun FullScreenMessageArea(message: Messages, hide: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        modifier = Modifier.verticalScroll(rememberScrollState())

    ) {
        Chapter(
            top = {
                if (message.messages.isNotEmpty()) {
                    Column {
                        Text(
                            text = "You",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.padding(16.dp))
                        CompositionLocalProvider(
                            LocalTextSelectionColors provides TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.onPrimary,
                                backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        ) {

                            SelectionContainer {

                                Text(
                                    text = message.messages[0].content,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                }



            },
            bottom = {
                if (message.messages.size > 1) {
                    Column {
                        Text(
                            text = "AI",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.inversePrimary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        CompositionLocalProvider(
                            LocalTextSelectionColors provides TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.onPrimary,
                                backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        ) {
                            SelectionContainer {
                                Text(
                                    text = message.messages[1].content,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }

                }

            }
        )

    }


    BackHandler {
        hide()
    }


}