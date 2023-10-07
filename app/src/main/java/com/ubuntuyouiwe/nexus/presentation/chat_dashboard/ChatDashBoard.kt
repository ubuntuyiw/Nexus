package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomDeleteState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomFilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomShortState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.ChatRoom
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.ChatRoomBanner
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.FilterDialog
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.FilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuItemCategory
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu.MenuScreen
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.ChatRoomsState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.WorkManagerState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.util.RolesCategory
import com.ubuntuyouiwe.nexus.presentation.util.ShortDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    userState: UserOperationState,
    onEvent: (ChatDashBoardEvent) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    val vibrator2 = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator


    var selectedChatRooms by remember {
        mutableStateOf<Set<ChatRoom>>(setOf())
    }
    val hasFavorited = selectedChatRooms.any { it.isFavorited }
    val hasNotFavorited = selectedChatRooms.any { !it.isFavorited }
    val hasPinned = selectedChatRooms.any { it.isPinned }
    val hasNotPinned = selectedChatRooms.any { !it.isPinned }
    var fabHeight by remember { mutableIntStateOf(0) }
    var fabPosition by remember { mutableFloatStateOf(0f) }

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
        mutableIntStateOf(5)
    }
    val nexus = stringResource(id = R.string.app_name)
    val deleting = stringResource(id = R.string.deleting)
    val errorOccurred = stringResource(id = R.string.error_occurred)
    val loggingOut = stringResource(id = R.string.logging_out)
    val retry = stringResource(id = R.string.retry)
    val archived = stringResource(id = R.string.archived)
    val archive = stringResource(id = R.string.archive)
    val removePin = stringResource(id = R.string.remove_pin)
    val newChat = stringResource(id = R.string.new_chat)
    val chatRooms = stringResource(id = R.string.chat_rooms)
    val filter = stringResource(id = R.string.filter)
    val sendEmail = stringResource(id = R.string.send_email)
    val noContentCreated = stringResource(id = R.string.no_content_created)
    val timeRemainingDeletion = stringResource(id = R.string.time_remaining_deletion)
    val cancel = stringResource(id = R.string.cancel)

    LaunchedEffect(key1 = workManagerState, key2 = chatRoomDeleteSate) {
        //TODO
        if (chatRoomDeleteSate.isSuccess) {
            if (workManagerState.isRunning) {
                hostState.showSnackbar(deleting)
            } else if (workManagerState.isFailed) {
                hostState.showSnackbar(errorOccurred)
            }
        } else if (chatRoomDeleteSate.isError) {
            hostState.showSnackbar(chatRoomDeleteSate.errorMessage)
        }

    }



    LaunchedEffect(key1 = stateSignOut) {
        if (stateSignOut.isLoading) {
            hostState.showSnackbar(loggingOut)
        } else if (stateSignOut.isError) {
            hostState.showSnackbar(stateSignOut.errorMessage)
        }
    }

    LaunchedEffect(key1 = chatRoomsState) {
        if (chatRoomsState.isError) {
            val result = hostState.showSnackbar(
                chatRoomsState.errorMessage,
                duration = SnackbarDuration.Indefinite,
                actionLabel = retry
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
                    Column {
                        if (selectedChatRooms.isEmpty()) {
                            if (chatRoomFilterState.data.isArchived) {

                                Text(
                                    text = archive,
                                    style = MaterialTheme.typography.titleMedium
                                )

                            } else {
                                Text(
                                    text = nexus,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                        } else {
                            Text(text = selectedChatRooms.size.toString())

                        }
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
                                        contentDescription = if (hasPinned) removePin else Icons.Default.PushPin.name,
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
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (!chatRoomFilterState.data.isArchived) {
                ExtendedFloatingActionButton(
                    text = { Text(text = newChat) },
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
                    contentColor = MaterialTheme.colorScheme.onPrimary,
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
        },
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
                            text = chatRooms,
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
                                text = filter,
                                style = MaterialTheme.typography.labelLarge,
                                color = filterColor,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = false
                            )
                        }
                    }

                }


                if (filterState.isDialogVisibility) {
                    ModalBottomSheet(
                        dragHandle = {},
                        onDismissRequest = {
                            onEvent(
                                ChatDashBoardEvent.ChangeFilterDialogVisibility(
                                    false
                                )
                            )
                        }, containerColor = MaterialTheme.colorScheme.background,
                        scrimColor = MaterialTheme.colorScheme.primary.copy(0.6f)
                    ) {
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
                                            RolesCategory.All -> {
                                                chatRoomFilterState.data.copy(
                                                    isNeutralMode = state,
                                                    isDebateArena = state,
                                                    isDoctor = state,
                                                    isChef = state,
                                                    isLiteratureTeacher = state,
                                                    isPhilosophy = state,
                                                    isSportsPolymath = state,
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

                                            RolesCategory.NeutralMode -> chatRoomFilterState.data.copy(
                                                isNeutralMode = state
                                            )


                                            RolesCategory.DebateArena -> chatRoomFilterState.data.copy(
                                                isDebateArena = state
                                            )

                                            RolesCategory.Doctor -> chatRoomFilterState.data.copy(
                                                isDoctor = state
                                            )

                                            RolesCategory.Chef -> chatRoomFilterState.data.copy(
                                                isChef = state
                                            )

                                            RolesCategory.SportsPolymath -> chatRoomFilterState.data.copy(
                                                isSportsPolymath = state
                                            )

                                            RolesCategory.LiteratureTeacher -> chatRoomFilterState.data.copy(
                                                isLiteratureTeacher = state
                                            )

                                            RolesCategory.Philosophy -> chatRoomFilterState.data.copy(
                                                isPhilosophy = state
                                            )

                                            RolesCategory.Lawyer -> chatRoomFilterState.data.copy(
                                                isLawyer = state
                                            )

                                            RolesCategory.RelationshipCoach -> chatRoomFilterState.data.copy(
                                                isRelationshipCoach = state
                                            )

                                            RolesCategory.IslamicScholar -> chatRoomFilterState.data.copy(
                                                isIslamicScholar = state
                                            )

                                            RolesCategory.Astrologer -> chatRoomFilterState.data.copy(
                                                isAstrologer = state
                                            )

                                            RolesCategory.Bishop -> chatRoomFilterState.data.copy(
                                                isBishop = state
                                            )

                                            RolesCategory.Psychologist -> chatRoomFilterState.data.copy(
                                                isPsychologist = state
                                            )

                                            RolesCategory.PhysicsTeacher -> chatRoomFilterState.data.copy(
                                                isPhysicsTeacher = state
                                            )

                                            RolesCategory.ChemistryTeacher -> chatRoomFilterState.data.copy(
                                                isChemistryTeacher = state
                                            )

                                            RolesCategory.BiologyTeacher -> chatRoomFilterState.data.copy(
                                                isBiologyTeacher = state
                                            )

                                            RolesCategory.MathematicsTeacher -> chatRoomFilterState.data.copy(
                                                isMathematicsTeacher = state
                                            )

                                            RolesCategory.GeographyTeacher -> chatRoomFilterState.data.copy(
                                                isGeographyTeacher = state
                                            )

                                            RolesCategory.EnglishTeacher -> chatRoomFilterState.data.copy(
                                                isEnglishTeacher = state
                                            )

                                            RolesCategory.HistoryTeacher -> chatRoomFilterState.data.copy(
                                                isHistoryTeacher = state
                                            )

                                            RolesCategory.Veterinarian -> chatRoomFilterState.data.copy(
                                                isVeterinarian = state
                                            )

                                            RolesCategory.SoftwareDeveloper -> chatRoomFilterState.data.copy(
                                                isSoftwareDeveloper = state
                                            )

                                            RolesCategory.TravelAdvisor -> chatRoomFilterState.data.copy(
                                                isTravelAdvisor = state
                                            )

                                            RolesCategory.JustFavorited -> chatRoomFilterState.data.copy(
                                                isFavorited = state
                                            )

                                            RolesCategory.JustArchived -> chatRoomFilterState.data.copy(

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
                        dragHandle = {},
                        onDismissRequest = {},
                        sheetState = sheetState,
                        containerColor = MaterialTheme.colorScheme.background,
                        scrimColor = MaterialTheme.colorScheme.primary.copy(0.6f)

                    ) {

                        MenuScreen { menuItemType ->
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                when (menuItemType) {
                                    MenuItemCategory.BUY_MESSAGES -> {
                                        navController.navigate(Screen.InAppPurchaseScreen.name)
                                    }

                                    MenuItemCategory.SETTINGS -> {
                                        navController.navigate(Screen.MainSettings.name)
                                    }

                                    MenuItemCategory.ARCHIVED -> {
                                        onEvent(
                                            ChatDashBoardEvent.ChatRoomFilterChange(
                                                chatRoomFilterState.data.copy(
                                                    isArchived = !chatRoomFilterState.data.isArchived
                                                )
                                            )
                                        )
                                    }

                                    MenuItemCategory.PRIVACY_POLICY -> {
                                        val link = "https://www.iubenda.com/privacy-policy/84531396"
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                        context.startActivity(intent)
                                    }

                                    MenuItemCategory.TERMS_OF_USE -> {
                                        navController.navigate(Screen.TermsOfUseScreen.name)

                                    }

                                    MenuItemCategory.RATE_US -> {
                                        val link =
                                            "https://play.google.com/store/apps/details?id=com.ubuntuyouiwe.nexus"
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                        context.startActivity(intent)

                                    }

                                    MenuItemCategory.HELP_CENTER -> {
                                        val email = "ubuntu@ubuntuyouiwe.com"
                                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:$email")
                                        }
                                        context.startActivity(
                                            Intent.createChooser(
                                                emailIntent,
                                                sendEmail
                                            )
                                        )


                                    }

                                    MenuItemCategory.SIGN_OUT -> {
                                        onEvent(ChatDashBoardEvent.LogOut)
                                    }
                                }
                            }


                        }
                    }

                }
                val state = rememberLazyListState()
                ChatRoomBanner()
                LazyColumn(modifier = Modifier.fillMaxSize(), state = state) {
                    itemsIndexed(chatRoomsState.data) { index, chatRoom ->
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
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                            val vibrator =
                                                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                                            vibrator.vibrate(
                                                CombinedVibration.createParallel(
                                                    VibrationEffect.createOneShot(
                                                        75,
                                                        VibrationEffect.DEFAULT_AMPLITUDE
                                                    )
                                                )
                                            )
                                        } else {
                                            vibrator2.vibrate(75)
                                        }
                                        selectedChatRooms + setOf(chatRoom)
                                    } else selectedChatRooms.filter { it != chatRoom }.toSet()
                                }
                            },
                            onLongClick = {
                                selectedChatRooms = if (!selectedChatRooms.contains(chatRoom)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                        val vibrator =
                                            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                                        vibrator.vibrate(
                                            CombinedVibration.createParallel(
                                                VibrationEffect.createOneShot(
                                                    75,
                                                    VibrationEffect.DEFAULT_AMPLITUDE
                                                )
                                            )
                                        )
                                    } else {
                                        vibrator2.vibrate(75)
                                    }
                                    selectedChatRooms + setOf(chatRoom)
                                } else selectedChatRooms.filter { it != chatRoom }.toSet()
                            }
                        )
                    }
                    item {
                        if (chatRoomsState.data.isEmpty()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = noContentCreated,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelLarge,
                                )

                            }
                        }

                    }
                }
            }
        }
        val snackbarHeight = 50.dp
        val snackbarBottomPadding = 28.dp
        LaunchedEffect(key1 = chatRoomDeleteSate.isSuccess && workManagerState.isEnqueued) {
            if (chatRoomDeleteSate.isSuccess && workManagerState.isEnqueued) {
                if (deleteTime >= 0) {

                    for (i in 5 downTo 0) {
                        deleteTime = i
                        delay(1000)

                    }
                } else deleteTime = 5
            } else deleteTime = 5

        }

        if (chatRoomDeleteSate.isSuccess && workManagerState.isEnqueued) {

            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(36.dp),
                    modifier = Modifier
                        .offset(
                            y = -(snackbarBottomPadding + with(LocalDensity.current) { fabHeight.toDp() }),
                            x = 0.dp
                        )
                        .height(snackbarHeight)
                        .widthIn(max = 716.dp)
                        .fillMaxWidth(0.88f)


                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {

                        Text(
                            text = "$timeRemainingDeletion $deleteTime",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Button(onClick = {
                            onEvent(
                                ChatDashBoardEvent.ChatRoomCancelDelete(
                                    chatRoomDeleteSate.data
                                )
                            )
                        }) {

                            Text(text = cancel)
                        }

                    }

                }
            }

        }


    }


}

@RequiresApi(Build.VERSION_CODES.S)
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
        val userState = UserOperationState()
        ChatDashBoard(
            navController,
            stateSignOut,
            chatRoomShortState,
            chatRoomFilterState,
            filterState,
            chatRoomsState,
            chatRoomDeleteSate,
            workManagerState,
            userState,
        ) {}
    }
}

