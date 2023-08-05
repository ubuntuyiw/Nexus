package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.ChatRoom
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.CreateChatRoomDialog
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.fab_style.PrimaryFab
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun ChatDashBoard(
    stateSignOut: SignOutState,
    createChatRoomState: CreateChatRoomState,
    dialogProperties: DialogSate,
    onEvent: (ChatDashBoardEvent) -> Unit
) {
    val hostState = remember { SnackbarHostState() }
    val more: ImageVector =
        ImageVector.vectorResource(id = R.drawable.baseline_more_vert_24)
    Scaffold(
        containerColor = Whisper,
        topBar = {
            PrimaryTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = { IconButton(onClick = { onEvent(ChatDashBoardEvent.LogOut)}) {
                    Icon(imageVector = more, contentDescription = "", tint = White)
                    
                } }
            )
        },
        floatingActionButton = {
            PrimaryFab() {
                onEvent(ChatDashBoardEvent.ChangeDialogVisibility(true))
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .fillMaxWidth()
            ) {

                Text(text = "Chat Rooms", style = MaterialTheme.typography.bodyLarge, color = Black)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                ) {

                    SecondaryButton(onClick = { }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Icon(
                                imageVector =  ImageVector.vectorResource(id = R.drawable.filter_icon),
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

            }
            if (createChatRoomState.dialogVisibility) {
                Dialog(onDismissRequest = { onEvent(ChatDashBoardEvent.ChangeDialogVisibility(false)) }) {
                    CreateChatRoomDialog()
                }
            }

            data class Data(
                val logo: Int = 0,
                val title: String = "Recipe",
                val role: String = "Chef",
                val lastMessage: String = "Bu son mesajım",
                val isFavorite: Boolean = false,
                val isPin: Boolean = false,

            )

            val arrayList = ArrayList<Data>()
            repeat(5000) {
                arrayList.add(Data())
            }




            LazyColumn() {

                items(arrayList) {
                    ChatRoom()
                }


            }


        }
    }


}

@Preview(showBackground = true)
@Composable
fun ChatDashBoardPreview() {
    NexusTheme {
        val stateSignOut = SignOutState()
        val createChatRoomState = CreateChatRoomState()
        val dialogProperties = DialogSate()
        ChatDashBoard(
            stateSignOut,
            createChatRoomState,
            dialogProperties,
        ) {}
    }
}

