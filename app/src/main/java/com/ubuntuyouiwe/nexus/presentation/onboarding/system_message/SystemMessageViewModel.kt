package com.ubuntuyouiwe.nexus.presentation.onboarding.system_message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.UpdateSystemMessageUseCase
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SystemMessageViewModel @Inject constructor(
    sharedState: SharedState,
    private val updateSystemMessageUseCase: UpdateSystemMessageUseCase
): ViewModel() {

    private val userState = sharedState.userState
    val systemMessage = mutableStateOf(userState.value.successData?.systemMessage?: "")

    private val _updateSystemMessageState = mutableStateOf(UpdateSystemMessageState())
    val updateSystemMessageState: State<UpdateSystemMessageState> = _updateSystemMessageState
    fun onEvent(event: SystemMessageEvent) {
        when(event) {
            is SystemMessageEvent.UserNameEnter -> {
                systemMessage.value = event.systemMessage
            }
            is SystemMessageEvent.UpdateSystemMessage -> {
                updateSystemMessage(systemMessage.value)
            }
        }
    }

    private fun updateSystemMessage(systemMessage: String) {
        updateSystemMessageUseCase(systemMessage).onEach {
            when(it) {
                is Resource.Loading -> {
                    _updateSystemMessageState.value = updateSystemMessageState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _updateSystemMessageState.value = updateSystemMessageState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }
                is Resource.Error -> {
                    _updateSystemMessageState.value = updateSystemMessageState.value.copy(
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