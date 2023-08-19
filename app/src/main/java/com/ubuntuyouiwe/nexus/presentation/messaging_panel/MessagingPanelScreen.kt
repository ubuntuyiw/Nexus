package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryVariantIconButton
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryVariantTextField
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.MessageArea
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.SpeechState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MessagingPanelScreen(
    navController: NavController,
    role: RoleState,
    messageTextFieldState: TextFieldState,
    sendMessageButtonState: ButtonState,
    getMessagesState: GetMessagesState,
    chatRoomState: ChatRoomState,
    speechState: SpeechState,
    onEvent: (MessagingPanelOnEvent) -> Unit
) {
    Scaffold(
        containerColor = Whisper,
        topBar = {
            PrimaryTopAppBar(
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            onEvent(
                                MessagingPanelOnEvent.NavigateUp(
                                    navController
                                )
                            )
                        }
                    ) {
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = Icons.Default.ArrowBack.name,
                            tint = White
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        GlideImage(
                            model = role.data.image,
                            contentDescription = role.data.name.EN,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }


                },
                title = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = chatRoomState.data.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.padding(start = 16.dp))
                        Text(text = role.data.name.EN, style = MaterialTheme.typography.titleSmall)
                    }

                }
            ) {

            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                ) {

                    PrimaryVariantTextField(
                        value = messageTextFieldState.text,
                        onValueChange = { onEvent(MessagingPanelOnEvent.EnterMessage(it)) },
                        placeholder = {
                            Text(
                                text = "Type a message",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .weight(88f)
                    )
                    Spacer(modifier = Modifier.weight(2f))

                    AnimatedVisibility(
                        visible = sendMessageButtonState.enabled,
                        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                        exit = scaleOut()
                    ) {

                        PrimaryVariantIconButton(
                            onClick = {
                                onEvent(
                                    MessagingPanelOnEvent.SendMessage(
                                        content = messageTextFieldState.text
                                    )
                                )
                            },
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Voice Recording",
                            )

                        }
                    }
                }
            }


        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                reverseLayout = true
            ) {

                getMessagesState.data.messages.let { list ->
                    itemsIndexed(list) { index, messages ->
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {


                            MessageArea(
                                messages,
                                index,
                                speechState,
                                onclick = { state ->
                                    onEvent(MessagingPanelOnEvent.Speak(state))
                                }
                            )


                        }

                    }
                }
            }
            if (chatRoomState.data.isNew) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.95f)
                ) {
                    Text(text = "İnfo bölümünden bilgilerinizi girerek daha sağlıklı bilgiler alabilirsiniz...")
                }
            }
        }


    }
}