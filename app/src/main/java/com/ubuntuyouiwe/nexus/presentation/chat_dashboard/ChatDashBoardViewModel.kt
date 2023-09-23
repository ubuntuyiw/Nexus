package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.use_case.GetRolesUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SignOutUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.ChatRoomUpdateUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomsUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.delete_chat_rooms.CancelWorkerUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.delete_chat_rooms.ChatRoomDeleteUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_filter.GetChatRoomFilterUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_filter.UpdateChatRoomFilterUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_short.GetChatRoomShortUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_short.UpdateChatRoomShortUseCase
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomDeleteState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomFilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomShortState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ModalBottomSheetState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.FilterState
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.ChatRoomsState
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.RolesState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.presentation.state.WorkManagerState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatDashBoardViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val sharedState: SharedState,
    private val getChatRoomsUseCase: GetChatRoomsUseCase,
    private val getRolesUseCase: GetRolesUseCase,
    private val getChatRoomFilterUseCase: GetChatRoomFilterUseCase,
    private val updateChatRoomFilterUseCase: UpdateChatRoomFilterUseCase,
    private val getChatRoomShortUseCase: GetChatRoomShortUseCase,
    private val updateChatRoomShortUseCase: UpdateChatRoomShortUseCase,
    private val chatRoomUpdateUseCase: ChatRoomUpdateUseCase,
    private val chatRoomDeleteUseCase: ChatRoomDeleteUseCase,
    private val cancelWorkerUseCase: CancelWorkerUseCase,
    private val application: Application

) : AndroidViewModel(application) {

    private val _rolesState = mutableStateOf(RolesState())

    private val _stateLogOut = mutableStateOf(SignOutState())
    val stateLogOut: State<SignOutState> = _stateLogOut


    private val _chatRoomsState = mutableStateOf(ChatRoomsState())
    val chatRoomsState: State<ChatRoomsState> = _chatRoomsState

    private val _userState = sharedState.userState
    val userState: State<UserOperationState> = _userState

    private val _menuState = mutableStateOf(ModalBottomSheetState())
    val menuState: State<ModalBottomSheetState> = _menuState

    private val _filter = mutableStateOf(FilterState())
    val filter: State<FilterState> = _filter

    private val _chatRoomFilterState = mutableStateOf(ChatRoomFilterState())
    val chatRoomFilterState: State<ChatRoomFilterState> = _chatRoomFilterState

    private val _chatRoomShortState = mutableStateOf(ChatRoomShortState())
    val chatRoomShortState: State<ChatRoomShortState> = _chatRoomShortState

    private val _chatRoomDeleteSate = mutableStateOf(ChatRoomDeleteState())
    val chatRoomDeleteSate: State<ChatRoomDeleteState> = _chatRoomDeleteSate

    private val _workManagerState = mutableStateOf(WorkManagerState())
    val workManagerState: State<WorkManagerState> = _workManagerState


    init {
        getChatRoomFilter()
        getChatRoomShort()
        getChatRooms(chatRoomShortState.value.data.isNewestFirst, chatRoomFilterState.value.data)
        getRoles()

    }

    fun onEvent(event: ChatDashBoardEvent) {
        when (event) {
            is ChatDashBoardEvent.LogOut -> {
                logOut()
            }

            is ChatDashBoardEvent.ChangeMenuVisibility -> {
                _menuState.value =
                    menuState.value.copy(isVisibility = event.isVisibility)
            }

            is ChatDashBoardEvent.ChatRoomsRetry -> {
                getChatRooms(
                    chatRoomShortState.value.data.isNewestFirst,
                    chatRoomFilterState.value.data
                )
            }

            is ChatDashBoardEvent.ChangeFilterDialogVisibility -> {
                _filter.value = filter.value.copy(isDialogVisibility = event.isVisibility)
            }

            is ChatDashBoardEvent.ChatRoomShortChange -> {
                updateShortDate(event.chatRoomShort)
            }

            is ChatDashBoardEvent.ChatRoomFilterChange -> {
                updateFilterRoles(event.chatRoomFilter)
            }

            is ChatDashBoardEvent.ChatRoomUpdate -> {
                chatRoomUpdate(event.chatRooms)
            }

            is ChatDashBoardEvent.ChatRoomDelete -> {
                chatRoomDelete(event.chatRooms)
            }
            is ChatDashBoardEvent.ChatRoomCancelDelete -> {
                cancelChatRoomDelete(event.id)
            }

        }
    }

    private fun getRoles() {
        getRolesUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _rolesState.value =
                        _rolesState.value.copy(isLoading = true, isSuccess = false, isError = false)
                }

                is Resource.Success -> {
                    it.data?.let { roles ->
                        _rolesState.value = _rolesState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            isError = false,
                            data = roles
                        )
                    }

                }

                is Resource.Error -> {
                    _rolesState.value = _rolesState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private var getChatRoomJob: Job? = Job()
    private fun getChatRooms(short: Boolean, filter: ChatRoomFilter) {
        getChatRoomJob?.cancel()
        getChatRoomJob = getChatRoomsUseCase(short, filter).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _chatRoomsState.value = chatRoomsState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false

                    )

                }

                is Resource.Success -> {
                    val data = resource.data

                    val updatedChatRooms = data?.messageResult?.map { chatRoom ->
                        val matchingRole =
                            _rolesState.value.data.first { it.type == chatRoom.roleId }
                        chatRoom.copy(
                            role = matchingRole,
                        )

                    }
                    updatedChatRooms?.let { chatRooms ->
                        _chatRoomsState.value = chatRoomsState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            isError = false,
                            isFromCache = data.isFromCache,
                            data = chatRooms
                        )
                    }


                }

                is Resource.Error -> {
                    _chatRoomsState.value = chatRoomsState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun chatRoomUpdate(chatRooms: List<ChatRoom>) {
        chatRoomUpdateUseCase(chatRooms).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {

                }

                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun chatRoomDelete(chatRooms: List<ChatRoom>) {
        chatRoomDeleteUseCase(chatRooms).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _chatRoomDeleteSate.value = chatRoomDeleteSate.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _chatRoomDeleteSate.value = chatRoomDeleteSate.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: UUID.randomUUID()
                    )
                    workManagerState(chatRoomDeleteSate.value.data)
                }

                is Resource.Error -> {
                    _chatRoomDeleteSate.value = chatRoomDeleteSate.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun workManagerState(id: UUID) {
        val workInfo = WorkManager.getInstance(application.applicationContext)
            .getWorkInfoByIdLiveData(id)
        workInfo.observeForever {
            when (it?.state) {
                WorkInfo.State.SUCCEEDED -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = true,
                        isEnqueued = false,
                        isRunning = false,
                        isFailed = false,
                        isBlocked = false,
                        isCancelled = false,
                    )
                    _chatRoomDeleteSate.value = ChatRoomDeleteState()
                }

                WorkInfo.State.FAILED -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = false,
                        isFailed = true,
                        isEnqueued = false,
                        isRunning = false,
                        isBlocked = false,
                        isCancelled = false,
                    )
                    _chatRoomDeleteSate.value = ChatRoomDeleteState()

                }

                WorkInfo.State.ENQUEUED -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = false,
                        isFailed = false,
                        isEnqueued = true,
                        isRunning = false,
                        isBlocked = false,
                        isCancelled = false,
                    )

                }

                WorkInfo.State.BLOCKED -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = false,
                        isFailed = false,
                        isEnqueued = false,
                        isBlocked = true,
                        isRunning = false,
                        isCancelled = false,
                    )

                }

                WorkInfo.State.CANCELLED -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = false,
                        isFailed = false,
                        isEnqueued = false,
                        isBlocked = false,
                        isCancelled = true,
                        isRunning = false,
                    )
                    /*_chatRoomDeleteSate.value = ChatRoomDeleteState()*/


                }

                WorkInfo.State.RUNNING -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isSucceeded = false,
                        isFailed = false,
                        isEnqueued = false,
                        isBlocked = false,
                        isCancelled = false,
                        isRunning = true,
                    )
                }

                else -> {
                    _workManagerState.value = workManagerState.value.copy(
                        isEnqueued = false,
                        isRunning = false,
                        isSucceeded = false,
                        isFailed = false,
                        isBlocked = false,
                        isCancelled = false,
                    )
                    _chatRoomDeleteSate.value = ChatRoomDeleteState()

                }

            }
        }
    }

    private fun cancelChatRoomDelete(id: UUID) {
        cancelWorkerUseCase(id)
    }

    private fun getChatRoomFilter() {
        getChatRoomFilterUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: ChatRoomFilter()
                    )
                }

                is Resource.Error -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun updateFilterRoles(chatRoomFilter: ChatRoomFilter) {
        updateChatRoomFilterUseCase(chatRoomFilter).onEach {
            when (it) {
                is Resource.Loading -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )

                }

                is Resource.Success -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = it.data ?: ChatRoomFilter()

                    )
                    getChatRooms(
                        chatRoomShortState.value.data.isNewestFirst,
                        chatRoomFilterState.value.data
                    )
                }

                is Resource.Error -> {
                    _chatRoomFilterState.value = chatRoomFilterState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message

                    )

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateShortDate(chatRoomShort: ChatRoomShort) {
        updateChatRoomShortUseCase(chatRoomShort).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: ChatRoomShort()
                    )

                    getChatRooms(
                        chatRoomShortState.value.data.isNewestFirst,
                        chatRoomFilterState.value.data
                    )

                }

                is Resource.Error -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getChatRoomShort() {
        getChatRoomShortUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )

                }

                is Resource.Success -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: ChatRoomShort()
                    )

                }

                is Resource.Error -> {
                    _chatRoomShortState.value = chatRoomShortState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun logOut() {
        signOutUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _stateLogOut.value =
                        stateLogOut.value.copy(isLoading = true, isSuccess = false, isError = false)
                }

                is Resource.Success -> {
                    _stateLogOut.value =
                        stateLogOut.value.copy(isLoading = false, isSuccess = true, isError = false)
                }

                is Resource.Error -> {
                    _stateLogOut.value = stateLogOut.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }

            }
        }.launchIn(viewModelScope)
    }
}