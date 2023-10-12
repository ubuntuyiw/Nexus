package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.Chapter
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState


@Composable
fun FullScreenMessageArea(message: Messages, userState: UserOperationState, hide: () -> Unit) {

    val nexus = stringResource(id = R.string.app_name)
    val you = stringResource(id = R.string.you)

    val userName = if (userState.successData?.name.isNullOrBlank()) you else userState.successData?.name
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())

    ) {
        Chapter(
            top = {
                if (message.messages.isNotEmpty()) {
                    Column {
                        Text(
                            text = userName?: you,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
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
                            text = nexus,
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