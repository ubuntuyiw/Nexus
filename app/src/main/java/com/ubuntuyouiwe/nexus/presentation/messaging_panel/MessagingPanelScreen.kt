package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ChatRoomUpdateState
import com.ubuntuyouiwe.nexus.presentation.main_activity.SettingsState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserMessagingDataState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.state.ChatRoomState
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.state.GetMessagesState
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.state.RoleState
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.state.SendMessageState
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.FullScreenMessageArea
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.FullScreenTextField
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.MessageArea
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.MessageAreaBanner
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets.RenameChatRoomDialog
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.util.ImageUrl.SHAKE
import kotlinx.coroutines.launch


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun MessagingPanelScreen(
    navController: NavController,
    role: RoleState,
    sendMessageState: SendMessageState,
    messageTextFieldState: TextFieldState,
    sendMessageButtonState: ButtonState,
    getMessagesState: GetMessagesState,
    chatRoomState: ChatRoomState,
    photoUri: Uri,
    settingsState: SettingsState,
    userState: UserOperationState,
    userMessagingDataState: UserMessagingDataState,
    chatRoomUpdateState: ChatRoomUpdateState,
    onEvent: (MessagingPanelOnEvent) -> Unit
) {
    var dropdownMenuState by remember {
        mutableStateOf(false)
    }

    val hostState = remember { SnackbarHostState() }
    var expendedTextField by remember {
        mutableStateOf(false)
    }
    var expendedMessageState by remember {
        mutableStateOf(false)
    }
    var expendedMessageContent by remember {
        mutableStateOf<Messages?>(null)
    }





    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val imageText by remember {
        mutableStateOf(savedStateHandle?.get<String>("image_text"))
    }
    LaunchedEffect(key1 = imageText) {
        imageText?.let {
            onEvent(MessagingPanelOnEvent.EnterMessage(messageTextFieldState.text + it))
            savedStateHandle?.remove<String>("image_text")
        }
    }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                val uriEncode = Uri.encode(photoUri.toString())
                navController.navigate(Screen.PhotoEditingScreen.name + "/${uriEncode}")
            }
        }
    )
    val focusManager = LocalFocusManager.current
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = settingsState) {
        onEvent(MessagingPanelOnEvent.ChangeSpeechListener)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            takePictureLauncher.launch(photoUri)
        } else {
            scope.launch {
                val result = hostState.showSnackbar(
                    message ="Permission denied. Please grant access from settings.",
                    actionLabel = "Settings",
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite)
                if (SnackbarResult.ActionPerformed == result) {
                    val intent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity.packageName, null)
                        data = uri
                    }
                    activity.startActivity(intent)

                }
            }

        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState) {
                Snackbar(snackbarData = it)
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
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
                            style = MaterialTheme.typography.titleMedium,
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            maxLines = 1,
                        )
                        Spacer(modifier = Modifier.padding(start = 16.dp))
                        Text(text = role.data.name.EN, style = MaterialTheme.typography.titleSmall)
                    }

                },
                actions = {
                    val data = getMessagesState.data.messages.firstOrNull { it.isSpeak }
                    data?.let {
                        AnimatedVisibility(visible = it.isSpeak) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = it.codeLanguage.uppercase(),
                                    style = MaterialTheme.typography.labelLarge,
                                )
                                PrimaryIconButton(onClick = {
                                    onEvent(MessagingPanelOnEvent.SetSpeechRate)
                                }) {

                                    Text(
                                        text = settingsState.successData.setSpeechRate.toString() + "x",
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                }
                            }

                        }
                    }

                    IconButton(onClick = {
                        dropdownMenuState = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = Icons.Default.MoreVert.name,
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
                                    imageVector = Icons.Default.Create,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = Icons.Default.Create.name
                                )
                            },
                            text = {
                                Text(
                                    text = "Rename",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }, onClick = {
                                dropdownMenuState = false
                                if (!chatRoomState.data.isNew) {
                                    onEvent(MessagingPanelOnEvent.RenameChange(
                                        chatRoomUpdateState.copy(
                                            dialogVisibility = true
                                        )
                                    )
                                    )
                                }

                            })

                    }
                    var newName by remember {
                        mutableStateOf("")
                    }
                    if (chatRoomUpdateState.dialogVisibility) {
                        Dialog(
                            onDismissRequest = {
                                onEvent(MessagingPanelOnEvent.RenameChange(
                                    chatRoomUpdateState.copy(
                                        dialogVisibility = false
                                    )
                                ))
                            },
                        ) {
                            RenameChatRoomDialog(
                                newName = newName,
                                newNameOnValueChange = { newName = it},
                                chatRoomUpdateState,
                                rejectOnClick = {
                                    onEvent(MessagingPanelOnEvent.RenameChange(
                                        chatRoomUpdateState.copy(
                                            dialogVisibility = false
                                        )
                                    ))
                                },
                                approvalOnClick = {
                                    onEvent(MessagingPanelOnEvent.ChatRoomUpdate(listOf(chatRoomState.data.copy(name = newName))))

                                }
                            )
                        }
                    }


                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    PrimaryTextField(
                        value = messageTextFieldState.text,
                        onValueChange = {
                            onEvent(MessagingPanelOnEvent.EnterMessage(it))
                        },
                        placeholder = {
                            Text(
                                text = "Type a message",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        enabled = !expendedTextField,
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    expendedTextField = true
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = Icons.Default.Fullscreen.name,
                                    modifier = Modifier.size(24.dp)
                                )

                            }
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                                            scope.launch {
                                                val result = hostState.showSnackbar(
                                                    message ="We need camera access to convert the photo you take into text.",
                                                    actionLabel = "Grant Permission",
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Indefinite)

                                                if (SnackbarResult.ActionPerformed == result) {
                                                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                                                }
                                            }

                                        } else {
                                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                                        }
                                    } else {
                                        takePictureLauncher.launch(photoUri)
                                    }

                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Camera,
                                    contentDescription = Icons.Default.Camera.name
                                )

                            }

                        },
                        maxLines = 8,
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .weight(88f)
                    )
                    Spacer(modifier = Modifier.weight(2f))


                    AnimatedVisibility(
                        visible = sendMessageButtonState.enabled && !sendMessageState.isLoading,
                        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                        exit = scaleOut()
                    ) {
                        Column {

                            IconButton(
                                onClick = {
                                    onEvent(
                                        MessagingPanelOnEvent.SendMessage(
                                            content = messageTextFieldState.text
                                        )
                                    )
                                    focusManager.clearFocus()
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                ),
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            MessageAreaBanner()
            LazyColumn(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize(),
                reverseLayout = true
            ) {

               this.stickyHeader {
                   Box(
                       modifier = Modifier
                           .background(MaterialTheme.colorScheme.background)
                           .fillMaxWidth(),
                       contentAlignment = Alignment.Center,
                   ) {
                       Text(
                           text = "Available Messages: " + userMessagingDataState.successData?.totalMessages.toString(),
                           style = MaterialTheme.typography.labelLarge,
                       )
                   }


               }
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
                                expendedMessage = {
                                    expendedMessageState = true
                                    expendedMessageContent = messages
                                },
                                speechStateChange = { state ->
                                    onEvent(MessagingPanelOnEvent.Speak(state))
                                }
                            )


                        }

                    }
                    item {
                        if (chatRoomState.data.isNew) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Please be specific and descriptive when requesting information or answers from Nexus. Be aware that the provided information or responses may be inaccurate",
                                    style = MaterialTheme.typography.labelLarge,
                                )
                                Spacer(modifier = Modifier.padding(16.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    GlideImage(model = SHAKE, contentDescription = "shake the phone", modifier = Modifier.size(50.dp))
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    Text(
                                        text = "Shake your phone quickly to earn message credits.",
                                        style = MaterialTheme.typography.labelLarge,
                                    )


                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val buyMessages = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                                color = MaterialTheme.typography.labelLarge.color,
                                            )

                                        ) {
                                            append("Purchase a message package from the")
                                        }

                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.inversePrimary,
                                                fontWeight = FontWeight.ExtraBold,
                                            )
                                        ) {
                                            pushStringAnnotation(
                                                tag = "go",
                                                annotation = "go"
                                            )
                                            append(" Buy Messages ")
                                            pop()
                                        }
                                        withStyle(
                                            style = SpanStyle(
                                                fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                                color = MaterialTheme.typography.labelLarge.color,
                                            )
                                        ) {
                                            append("screen.")
                                        }

                                    }

                                    Spacer(modifier = Modifier.padding(8.dp))
                                    GlideImage(model = R.drawable.goldmessage, contentDescription = "Buy Messages", modifier = Modifier.size(50.dp))
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    ClickableText(
                                        text =  buyMessages,
                                        onClick = { offset ->
                                            buyMessages.getStringAnnotations(
                                                tag = "go",
                                                start = offset, end = offset
                                            ).firstOrNull()?.let { annotation ->
                                                when (annotation.item) {
                                                    "go" -> {
                                                        navController.navigate(Screen.InAppPurchaseScreen.name)
                                                    }
                                                }
                                            }
                                        },
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                            }
                        }
                    }

                }

            }

        }


    }
    AnimatedVisibility(
        visible = expendedTextField,
        enter = scaleIn() + fadeIn(initialAlpha = 0.3f),
        exit = scaleOut() + fadeOut(targetAlpha = 0.3f)
    ) {
        FullScreenTextField(
            messageText = messageTextFieldState.text,
            onValueChange = { onEvent(MessagingPanelOnEvent.EnterMessage(it)) },
            visibility = expendedTextField
        ) {
            expendedTextField = false
        }
    }

    AnimatedVisibility(
        visible = expendedMessageState,
        enter = scaleIn() + fadeIn(initialAlpha = 0.3f),
        exit = scaleOut() + fadeOut(targetAlpha = 0.3f),
    ) {
        expendedMessageContent?.let {
            FullScreenMessageArea(it, userState) {
                expendedMessageState = false
            }
        }

    }


}