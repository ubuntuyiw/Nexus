package com.ubuntuyouiwe.nexus.presentation.onboarding.user_name

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.UpdateDisplayNameUseCase
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(
    sharedState: SharedState,
    private val updateDisplayNameUseCase: UpdateDisplayNameUseCase
): ViewModel() {


    private val userState = sharedState.userState
    val userName = mutableStateOf(userState.value.successData?.name?: "")

    private val _updateDisplayNameState = mutableStateOf(UpdateDisplayNameState())
    val updateDisplayNameState: State<UpdateDisplayNameState> = _updateDisplayNameState



    fun onEvent(event: UserNameEvent) {
        when(event) {
            is UserNameEvent.UserNameEnter -> {
                userName.value = event.userName
            }
            is UserNameEvent.UpdateUserName -> {
                updateDisplayName(event.userName)
            }
            is UserNameEvent.UpdateDisplayNameStateReset -> {
                _updateDisplayNameState.value = UpdateDisplayNameState()
            }
        }
    }
    private fun updateDisplayName(name: String) {
        updateDisplayNameUseCase(name).onEach {
            when(it) {
                is Resource.Loading -> {
                    _updateDisplayNameState.value = _updateDisplayNameState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )

                }
                is Resource.Success -> {
                    _updateDisplayNameState.value = _updateDisplayNameState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }
                is Resource.Error -> {
                    _updateDisplayNameState.value = _updateDisplayNameState.value.copy(
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