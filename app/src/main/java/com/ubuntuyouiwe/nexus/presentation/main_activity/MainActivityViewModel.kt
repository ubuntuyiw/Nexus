package com.ubuntuyouiwe.nexus.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.AuthStateUseCase
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase,
    private val sharedState: SharedState
) : ViewModel() {

    private val _userOperationState = mutableStateOf(UserOperationState())
    val userOperationState: State<UserOperationState> = _userOperationState

    private val _authListenerRetryButton = mutableStateOf(ButtonState())
    val authListenerRetryButton: State<ButtonState> = _authListenerRetryButton

    private val _isDarkMode = sharedState.isDarkTheme
    val isDarkMode: State<Boolean> = _isDarkMode
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Retry -> {
                getAuthStateListener()
            }
        }
    }

    init {
        getAuthStateListener()
    }

    private fun getAuthStateListener() {
        authStateUseCase().onEach {
            when (it) {
                is Resource.Loading -> {

                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = false,
                        isError = false,
                        isLoading = true
                    )
                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = false)
                }

                is Resource.Success -> {
                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = true,
                        successData = it.data,
                        isError = false,
                        isLoading = false
                    )
                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = true)
                }

                is Resource.Error -> {
                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message,
                        isLoading = false
                    )
                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = true)
                }



            }
        }.launchIn(viewModelScope)
    }
}