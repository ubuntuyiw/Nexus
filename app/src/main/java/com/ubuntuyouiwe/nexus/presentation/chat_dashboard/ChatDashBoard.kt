package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideLazyListPreloader
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomsState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.CreateChatRoomState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.DialogSate
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ModalBottomSheetState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.RolesState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.ChatRoom
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.dialog.CreateChatRoomDialog
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuItemType
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuScreen
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.fab_style.PrimaryFab
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ChatDashBoard(
    navController: NavController,
    stateSignOut: SignOutState,
    createChatRoomState: CreateChatRoomState,
    dialogState: DialogSate,
    menuState: ModalBottomSheetState,
    roles: RolesState,
    chatRoomsState: ChatRoomsState,
    onEvent: (ChatDashBoardEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = stateSignOut) {
        if (stateSignOut.isLoading) {
            hostState.showSnackbar("Logging out...")
        }  else if (stateSignOut.isError) {
            hostState.showSnackbar(stateSignOut.errorMessage)
        }
    }

    Scaffold(
        containerColor = Whisper,
        topBar = {
            PrimaryTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            onEvent(ChatDashBoardEvent.ChangeMenuVisibility(true))
                            sheetState.partialExpand()
                        }

                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_more_vert_24),
                            contentDescription = "",
                            tint = White
                        )

                    }
                }
            )
        },
        floatingActionButton = {
            PrimaryFab() {
                onEvent(ChatDashBoardEvent.ChangeDialogVisibility(true))
            }
        },
        snackbarHost = {
            SnackbarHost(hostState) { data ->
                PrimarySnackbar(snackbarData = data)
            }
        }
    ) { paddingValues ->

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            if (chatRoomsState.isFromCache || chatRoomsState.isLoading)
                PrimaryCircularProgressIndicator()

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        text = "Chat Rooms",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Black
                    )

                    SecondaryButton(onClick = {

                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                                contentDescription = "Short Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "Filter",
                                style = MaterialTheme.typography.labelLarge,
                                color = Black,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false
                            )
                        }
                    }

                }
                if (createChatRoomState.dialogVisibility) {
                    Dialog(onDismissRequest = {
                        onEvent(
                            ChatDashBoardEvent.ChangeDialogVisibility(
                                false
                            )
                        )
                    }) {

                        CreateChatRoomDialog(roles) { selectedRole ->
                            onEvent(ChatDashBoardEvent.ChangeDialogVisibility(false))
                            navController.navigate(Screen.MessagingPanel.name + "/${selectedRole.type}/${null}")
                        }
                    }
                }


                if (sheetState.isVisible || (sheetState.currentValue != sheetState.targetValue)) {
                    ModalBottomSheet(
                        onDismissRequest = {},
                        sheetState = sheetState


                    ) {

                        MenuScreen() { menuItemType ->
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                when (menuItemType) {
                                    MenuItemType.PREMIUM -> {

                                    }

                                    MenuItemType.SETTINGS -> {
                                        scope.launch { sheetState.expand() }
                                    }

                                    MenuItemType.PRIVACY_POLICY -> {
                                    }

                                    MenuItemType.TERMS_OF_USE -> {
                                    }

                                    MenuItemType.RATE_USE -> {
                                    }

                                    MenuItemType.TRASH_BIN -> {

                                    }

                                    MenuItemType.HELP_CENTER -> {

                                    }

                                    MenuItemType.SIGN_OUT -> {
                                        onEvent(ChatDashBoardEvent.LogOut)
                                    }
                                }
                            }


                        }
                    }

                }
                val state = rememberLazyListState()
                val imageList = chatRoomsState.data.map { it.role.image.any() }
                GlideLazyListPreloader(
                    state = state,
                    data = imageList,
                    size = Size(40f, 40f),
                    numberOfItemsToPreload = 1
                ) { item, requestBuilder ->
                    requestBuilder.load(item)
                }

                LazyColumn(modifier = Modifier.fillMaxSize(), state = state) {
                    itemsIndexed(chatRoomsState.data) { _, chatRoom ->
                        ChatRoom(chatRoom) {
                            chatRoom.role.let { roles ->
                                roles.let {
                                    navController.navigate(Screen.MessagingPanel.name + "/${it.type}/${chatRoom.id}")

                                }
                            }
                        }
                    }


                }


            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun ChatDashBoardPreview() {
    NexusTheme {
        val navController = rememberNavController()
        val stateSignOut = SignOutState()
        val createChatRoomState = CreateChatRoomState()
        val menuState = ModalBottomSheetState()
        val dialogState = DialogSate()
        val roles = RolesState()
        val chatRoomsState = ChatRoomsState(isSuccess = true)
        ChatDashBoard(
            navController,
            stateSignOut,
            createChatRoomState,
            dialogState,
            menuState,
            roles,
            chatRoomsState,
        ) {}
    }
}

