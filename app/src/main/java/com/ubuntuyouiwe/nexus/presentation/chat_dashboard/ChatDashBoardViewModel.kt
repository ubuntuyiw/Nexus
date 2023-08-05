package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SignOutUseCase
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatDashBoardViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase

) : ViewModel() {

    private val _stateLogOut = mutableStateOf(SignOutState())
    val stateLogOut: State<SignOutState> = _stateLogOut

    private val _createChatRoomState = mutableStateOf(CreateChatRoomState())
    val createChatRoomState: State<CreateChatRoomState> = _createChatRoomState
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
        }
    }

    private fun logOut() {
        signOutUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _stateLogOut.value =
                        stateLogOut.value.copy(isLoading = true, errorMessage = "", success = "")
                }

                is Resource.Success -> {
                    _stateLogOut.value =
                        stateLogOut.value.copy(
                            isLoading = false,
                            errorMessage = "",
                            success = "Success"
                        )
                }

                is Resource.Error -> {
                    _stateLogOut.value = stateLogOut.value.copy(
                        isLoading = false,
                        errorMessage = it.message,
                        success = ""
                    )
                }

            }
        }.launchIn(viewModelScope)
    }
}