package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideLazyListPreloader
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomDeleteState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomFilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomShortState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.ChatRoom
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.FilterDialog
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.FilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuItemType
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuScreen
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.ChatRoomsState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.WorkManagerState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White
import com.ubuntuyouiwe.nexus.presentation.util.RolesFilter
import com.ubuntuyouiwe.nexus.presentation.util.ShortDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
)
@Composable
fun ChatDashBoard(
    navController: NavController,
    stateSignOut: SignOutState,
    chatRoomShortState: ChatRoomShortState,
    chatRoomFilterState: ChatRoomFilterState,
    filterState: FilterState,
    chatRoomsState: ChatRoomsState,
    chatRoomDeleteSate: ChatRoomDeleteState,
    workManagerState: WorkManagerState,
    onEvent: (ChatDashBoardEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    var selectedChatRooms by remember {
        mutableStateOf<Set<ChatRoom>>(setOf())
    }
    val hasFavorited = selectedChatRooms.any { it.isFavorited }
    val hasNotFavorited = selectedChatRooms.any { !it.isFavorited }
    val hasPinned = selectedChatRooms.any { it.isPinned }
    val hasNotPinned = selectedChatRooms.any { !it.isPinned }
    var fabHeight by remember { mutableStateOf(0) }
    var fabPosition by remember { mutableStateOf(0f) }

    val topAppBarColor =
        if (selectedChatRooms.isNotEmpty()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

    systemUiController.setStatusBarColor(topAppBarColor)

    if (chatRoomFilterState.data.isArchived) {
        BackHandler {
            onEvent(
                ChatDashBoardEvent.ChatRoomFilterChange(
                    chatRoomFilterState.data.copy(
                        isArchived = false
                    )
                )
            )
        }
    }
    if (selectedChatRooms.isNotEmpty()) {
        BackHandler {
            selectedChatRooms = setOf()
        }
    }
    var deleteTime by remember {
        mutableIntStateOf(10)
    }
    var hasShownSnackbarForDeleteTime by remember { mutableStateOf(false) }
    LaunchedEffect(workManagerState) {
        if (workManagerState.isEnqueued) {
            while(true) {
                delay(1000)
                deleteTime--
            }
        }

    }

    LaunchedEffect(key1 = workManagerState, key2 = chatRoomDeleteSate,) {
        if (chatRoomDeleteSate.isSuccess) {
            if (workManagerState.isEnqueued) {

                if (!hasShownSnackbarForDeleteTime) {
                    hasShownSnackbarForDeleteTime = true

                    val snackbarResult = hostState.showSnackbar(
                        message = "Silinme işlemi başlatılıyor: $deleteTime",
                        actionLabel = "İptal",
                        duration = SnackbarDuration.Indefinite
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        onEvent(ChatDashBoardEvent.ChatRoomCancelDelete(
                            chatRoomDeleteSate.data
                        ))
                    }
                }

            } else if (workManagerState.isRunning) {
                hostState.showSnackbar("Siliniyor")
            } else if(workManagerState.isCancelled) {
                hostState.showSnackbar("iptal edildi")
            } else if(workManagerState.isSucceeded) {
                hostState.showSnackbar("Silindi")
            } else if(workManagerState.isFailed) {
                hostState.showSnackbar("Hata")
            }
        } else if (chatRoomDeleteSate.isError) {
            hostState.showSnackbar(chatRoomDeleteSate.errorMessage)
        }

    }



    LaunchedEffect(key1 = stateSignOut) {
        if (stateSignOut.isLoading) {
            hostState.showSnackbar("Logging out...")
        } else if (stateSignOut.isError) {
            hostState.showSnackbar(stateSignOut.errorMessage)
        }
    }

    LaunchedEffect(key1 = chatRoomsState) {
        if (chatRoomsState.isError) {
            val result = hostState.showSnackbar(
                chatRoomsState.errorMessage,
                duration = SnackbarDuration.Indefinite,
                actionLabel = "Retry"
            )
            if (result == SnackbarResult.ActionPerformed) {
                onEvent(ChatDashBoardEvent.ChatRoomsRetry)
            }
        }

    }
    LaunchedEffect(key1 = chatRoomShortState.isSuccess) {
        if (chatRoomShortState.isSuccess) {
            onEvent(ChatDashBoardEvent.ChatRoomsRetry)
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = {
                    if (selectedChatRooms.isEmpty()) {
                        if (chatRoomFilterState.data.isArchived) {

                            Text(
                                text = "Archived",
                                style = MaterialTheme.typography.titleMedium
                            )

                        } else {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    } else {
                        Text(text = selectedChatRooms.size.toString())

                    }
                },
                navigationIcon = {
                    if (selectedChatRooms.isEmpty()) {
                        if (chatRoomFilterState.data.isArchived) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .clickable {
                                        onEvent(
                                            ChatDashBoardEvent.ChatRoomFilterChange(
                                                chatRoomFilterState.data.copy(
                                                    isArchived = false
                                                )
                                            )
                                        )
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = Icons.Default.ArrowBack.name
                                )
                            }

                        }
                    } else {
                        IconButton(onClick = {
                            selectedChatRooms = setOf()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = Icons.Default.ArrowBack.name,
                                tint = White,
                                modifier = Modifier.size(24.dp)
                            )

                        }

                    }

                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (selectedChatRooms.isEmpty()) {
                            IconButton(onClick = {
                                scope.launch {
                                    onEvent(ChatDashBoardEvent.ChangeMenuVisibility(true))
                                    sheetState.partialExpand()
                                }

                            }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = Icons.Default.MoreVert.name,
                                    tint = White,
                                    modifier = Modifier.size(24.dp)
                                )

                            }
                        } else {

                            IconButton(onClick = {
                                onEvent(
                                    ChatDashBoardEvent.ChatRoomDelete(
                                        selectedChatRooms.toList()
                                    )
                                )
                                selectedChatRooms = setOf()

                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = Icons.Default.Delete.name,
                                    tint = White,
                                    modifier = Modifier.size(24.dp)
                                )

                            }

                            if (!(hasPinned && hasNotPinned)) {
                                IconButton(onClick = {
                                    onEvent(
                                        ChatDashBoardEvent.ChatRoomUpdate(
                                            selectedChatRooms.map {
                                                it.copy(
                                                    isPinned = !hasPinned
                                                )
                                            }.toList()
                                        )
                                    )
                                    selectedChatRooms = setOf()

                                }) {
                                    Icon(
                                        imageVector = if (hasPinned) ImageVector.vectorResource(
                                            id = R.drawable.remove_pin
                                        ) else Icons.Default.PushPin,
                                        contentDescription = if (hasPinned) "Remove Pin" else Icons.Default.PushPin.name,
                                        tint = White,
                                        modifier = Modifier.size(24.dp)
                                    )

                                }
                            }


                            if (!(hasFavorited && hasNotFavorited)) {
                                IconButton(onClick = {
                                    onEvent(
                                        ChatDashBoardEvent.ChatRoomUpdate(
                                            selectedChatRooms.map {
                                                it.copy(
                                                    isFavorited = !hasFavorited
                                                )
                                            }.toList()
                                        )
                                    )
                                    selectedChatRooms = setOf()

                                }) {
                                    Icon(
                                        imageVector = if (hasFavorited) Icons.Default.HeartBroken else Icons.Default.Favorite,
                                        contentDescription = if (hasFavorited) Icons.Default.HeartBroken.name else Icons.Default.Favorite.name,
                                        tint = White,
                                        modifier = Modifier.size(24.dp)
                                    )

                                }
                            }

                            IconButton(onClick = {
                                onEvent(
                                    ChatDashBoardEvent.ChatRoomUpdate(
                                        selectedChatRooms.map {
                                            it.copy(
                                                isArchived = !chatRoomFilterState.data.isArchived
                                            )
                                        }.toList()
                                    )
                                )
                                selectedChatRooms = setOf()

                            }) {
                                Icon(
                                    imageVector = if (chatRoomFilterState.data.isArchived) Icons.Default.Unarchive else Icons.Default.Archive,
                                    contentDescription = if (chatRoomFilterState.data.isArchived) Icons.Default.Unarchive.name else Icons.Default.Archive.name,
                                    tint = White,
                                    modifier = Modifier.size(24.dp)
                                )

                            }

                        }
                    }


                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        floatingActionButton = {
            if (!chatRoomFilterState.data.isArchived) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "New Chat") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = Icons.Default.Chat.name,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onClick = { navController.navigate(Screen.CreateChatRoomScreen.name) },
                    expanded = false,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = White,
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                            fabHeight = coordinates.size.height
                            fabPosition = coordinates.positionInRoot().y
                        }
                )
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "Chat Rooms",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    val filterColor = if (
                        chatRoomFilterState.data.isAllRoles &&
                        chatRoomShortState.data.isNewestFirst &&
                        !chatRoomFilterState.data.isFavorited
                    ) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.inversePrimary

                    val filterIcon = if (
                        chatRoomFilterState.data.isAllRoles &&
                        chatRoomShortState.data.isNewestFirst &&
                        !chatRoomFilterState.data.isFavorited
                    ) Icons.Default.FilterAlt
                    else Icons.Default.FilterAltOff

                    SecondaryButton(onClick = {
                        onEvent(ChatDashBoardEvent.ChangeFilterDialogVisibility(true))
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {

                            Icon(
                                imageVector = filterIcon,
                                contentDescription = filterIcon.name,
                                tint = filterColor
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "Filter",
                                style = MaterialTheme.typography.labelLarge,
                                color = filterColor,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false
                            )
                        }
                    }

                }


                if (filterState.isDialogVisibility) {
                    ModalBottomSheet(onDismissRequest = {
                        onEvent(
                            ChatDashBoardEvent.ChangeFilterDialogVisibility(
                                false
                            )
                        )
                    }, containerColor = MaterialTheme.colorScheme.background) {
                        FilterDialog(
                            chatRoomShortState,
                            chatRoomShortOnEvent = {
                                onEvent(
                                    ChatDashBoardEvent.ChatRoomShortChange(
                                        when (it) {
                                            ShortDate.NewestFirst -> ChatRoomShort(isNewestFirst = true)
                                            ShortDate.LatestFirst -> ChatRoomShort(isNewestFirst = false)
                                        }
                                    )
                                )

                            },
                            chatRoomFilterState,
                            setAllSelectedRole = {
                                onEvent(
                                    ChatDashBoardEvent.ChatRoomFilterChange(
                                        chatRoomFilterState.data.copy(
                                            isAllRoles = it
                                        )
                                    )
                                )
                            },
                            chatRoomFilterOnEvent = { state, roles ->
                                onEvent(
                                    ChatDashBoardEvent.ChatRoomFilterChange(
                                        when (roles) {
                                            RolesFilter.All -> {
                                                chatRoomFilterState.data.copy(
                                                    isNeutralMode = state,
                                                    isDebateArena = state,
                                                    isDoctor = state,
                                                    isChef = state,
                                                    isLawyer = state,
                                                    isRelationshipCoach = state,
                                                    isIslamicScholar = state,
                                                    isAstrologer = state,
                                                    isBishop = state,
                                                    isPsychologist = state,
                                                    isPhysicsTeacher = state,
                                                    isChemistryTeacher = state,
                                                    isBiologyTeacher = state,
                                                    isMathematicsTeacher = state,
                                                    isGeographyTeacher = state,
                                                    isEnglishTeacher = state,
                                                    isHistoryTeacher = state,
                                                    isVeterinarian = state,
                                                    isSoftwareDeveloper = state,
                                                    isTravelAdvisor = state
                                                )
                                            }

                                            RolesFilter.NeutralMode -> chatRoomFilterState.data.copy(
                                                isNeutralMode = state
                                            )

                                            RolesFilter.DebateArena -> chatRoomFilterState.data.copy(
                                                isDebateArena = state
                                            )

                                            RolesFilter.Doctor -> chatRoomFilterState.data.copy(
                                                isDoctor = state
                                            )

                                            RolesFilter.Chef -> chatRoomFilterState.data.copy(isChef = state)
                                            RolesFilter.Lawyer -> chatRoomFilterState.data.copy(
                                                isLawyer = state
                                            )

                                            RolesFilter.RelationshipCoach -> chatRoomFilterState.data.copy(
                                                isRelationshipCoach = state
                                            )

                                            RolesFilter.IslamicScholar -> chatRoomFilterState.data.copy(
                                                isIslamicScholar = state
                                            )

                                            RolesFilter.Astrologer -> chatRoomFilterState.data.copy(
                                                isAstrologer = state
                                            )

                                            RolesFilter.Bishop -> chatRoomFilterState.data.copy(
                                                isBishop = state
                                            )

                                            RolesFilter.Psychologist -> chatRoomFilterState.data.copy(
                                                isPsychologist = state
                                            )

                                            RolesFilter.PhysicsTeacher -> chatRoomFilterState.data.copy(
                                                isPhysicsTeacher = state
                                            )

                                            RolesFilter.ChemistryTeacher -> chatRoomFilterState.data.copy(
                                                isChemistryTeacher = state
                                            )

                                            RolesFilter.BiologyTeacher -> chatRoomFilterState.data.copy(
                                                isBiologyTeacher = state
                                            )

                                            RolesFilter.MathematicsTeacher -> chatRoomFilterState.data.copy(
                                                isMathematicsTeacher = state
                                            )

                                            RolesFilter.GeographyTeacher -> chatRoomFilterState.data.copy(
                                                isGeographyTeacher = state
                                            )

                                            RolesFilter.EnglishTeacher -> chatRoomFilterState.data.copy(
                                                isEnglishTeacher = state
                                            )

                                            RolesFilter.HistoryTeacher -> chatRoomFilterState.data.copy(
                                                isHistoryTeacher = state
                                            )

                                            RolesFilter.Veterinarian -> chatRoomFilterState.data.copy(
                                                isVeterinarian = state
                                            )

                                            RolesFilter.SoftwareDeveloper -> chatRoomFilterState.data.copy(
                                                isSoftwareDeveloper = state
                                            )

                                            RolesFilter.TravelAdvisor -> chatRoomFilterState.data.copy(
                                                isTravelAdvisor = state
                                            )

                                            RolesFilter.JustFavorited -> chatRoomFilterState.data.copy(
                                                isFavorited = state
                                            )

                                            RolesFilter.JustArchived -> chatRoomFilterState.data.copy(

                                                isArchived = state
                                            )


                                        }
                                    )


                                )


                            }
                        )
                    }
                }






                if (sheetState.isVisible || (sheetState.currentValue != sheetState.targetValue)) {
                    ModalBottomSheet(
                        onDismissRequest = {},
                        sheetState = sheetState,
                        containerColor = MaterialTheme.colorScheme.background,


                        ) {

                        MenuScreen { menuItemType ->
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

                                    MenuItemType.RATE_US -> {
                                        val intent = Intent()
                                        intent.action = "com.android.settings.TTS_SETTINGS"
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        context.startActivity(intent)
                                    }

                                    MenuItemType.ARCHIVED -> {
                                        onEvent(
                                            ChatDashBoardEvent.ChatRoomFilterChange(
                                                chatRoomFilterState.data.copy(
                                                    isArchived = !chatRoomFilterState.data.isArchived
                                                )
                                            )
                                        )
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
                        ChatRoom(
                            chatRoom,
                            selectedChatRooms,
                            onClick = {
                                if (selectedChatRooms.isEmpty()) {
                                    chatRoom.role.let { roles ->
                                        roles.let {
                                            navController.navigate(Screen.MessagingPanel.name + "/${it.type}/${chatRoom.id}")

                                        }
                                    }
                                } else {
                                    selectedChatRooms = if (!selectedChatRooms.contains(chatRoom)) {
                                        selectedChatRooms + setOf(chatRoom)
                                    } else selectedChatRooms.filter { it != chatRoom }.toSet()


                                }

                            },
                            onLongClick = {
                                selectedChatRooms = if (!selectedChatRooms.contains(chatRoom)) {
                                    selectedChatRooms + setOf(chatRoom)
                                } else selectedChatRooms.filter { it != chatRoom }.toSet()


                            }
                        )
                    }


                }


            }
        }
        val snackbarHeight = 56.dp
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .offset(y = -(16.dp + with(LocalDensity.current) { fabHeight.toDp()}), x = 0.dp)
                    .height(snackbarHeight)
                    .fillMaxWidth()
                    .background(Color.White)

            ) {

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
        val filterState = FilterState()
        val chatRoomsState = ChatRoomsState(isSuccess = true)
        val chatRoomFilterState = ChatRoomFilterState()
        val chatRoomShortState = ChatRoomShortState()
        val chatRoomDeleteSate = ChatRoomDeleteState()
        val workManagerState = WorkManagerState()
        ChatDashBoard(
            navController,
            stateSignOut,
            chatRoomShortState,
            chatRoomFilterState,
            filterState,
            chatRoomsState,
            chatRoomDeleteSate,
            workManagerState,
        ) {}
    }
}

