package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.GetRolesUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SignOutUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.firestore.GetChatRoomsUseCase
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomsState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.CreateChatRoomState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.DialogSate
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ModalBottomSheetState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.RolesState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatDashBoardViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getChatRoomsUseCase: GetChatRoomsUseCase,
    private val getRolesUseCase: GetRolesUseCase
) : ViewModel() {

    private val _rolesState = mutableStateOf<RolesState>(RolesState())
    val rolesState: State<RolesState> = _rolesState

    private val _stateLogOut = mutableStateOf(SignOutState())
    val stateLogOut: State<SignOutState> = _stateLogOut

    private val _createChatRoomState = mutableStateOf(CreateChatRoomState())
    val createChatRoomState: State<CreateChatRoomState> = _createChatRoomState

    private val _chatRoomsState = mutableStateOf(ChatRoomsState())
    val chatRoomsState: State<ChatRoomsState> = _chatRoomsState


    private val _menuState = mutableStateOf(ModalBottomSheetState())
    val menuState: State<ModalBottomSheetState> = _menuState


    init {
        getRoles()
        getChatRooms()
    }


    val dialogProperties by mutableStateOf(DialogSate())
    fun onEvent(event: ChatDashBoardEvent) {
        when (event) {
            is ChatDashBoardEvent.LogOut -> {
                logOut()
            }

            is ChatDashBoardEvent.ChangeDialogVisibility -> {
                _createChatRoomState.value =
                    createChatRoomState.value.copy(dialogVisibility = event.isVisibility)
            }

            is ChatDashBoardEvent.ChangeMenuVisibility -> {
                _menuState.value =
                    menuState.value.copy(isVisibility = event.isVisibility)
            }
        }
    }

    private fun getRoles() {
        getRolesUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _rolesState.value =
                        rolesState.value.copy(isLoading = true, isSuccess = false, isError = false)
                }

                is Resource.Success -> {
                    it.data?.let { roles ->
                        _rolesState.value = rolesState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            isError = false,
                            data = roles
                        )
                    }

                }

                is Resource.Error -> {
                    _rolesState.value = rolesState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getChatRooms() {
        getChatRoomsUseCase().onEach { resource ->
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
                            rolesState.value.data.first { it.type == chatRoom.roleId }
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