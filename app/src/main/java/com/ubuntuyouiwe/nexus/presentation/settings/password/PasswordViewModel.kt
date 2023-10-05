package com.ubuntuyouiwe.nexus.presentation.settings.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.UpdatePasswordUseCase
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase

): ViewModel() {
    private val _passwordTextFieldState = mutableStateOf(TextFieldState())
    val passwordTextFieldState: State<TextFieldState> = _passwordTextFieldState

    private val _passwordUpdateState = mutableStateOf(PasswordUpdateState())
    val passwordUpdateState: State<PasswordUpdateState> = _passwordUpdateState

    fun onEvent(event: PasswordEvent) {
        when(event) {
            is PasswordEvent.ChangePasswordVisibility -> {
                _passwordTextFieldState.value =
                    passwordTextFieldState.value.copy(isVisibility = !passwordTextFieldState.value.isVisibility)
            }
            is PasswordEvent.ChangePasswordTextField -> {
                _passwordTextFieldState.value = passwordTextFieldState.value.copy(text = event.text)
            }
            is PasswordEvent.ChangePassword -> {
                updatePassword(event.newPassword)
            }
        }
    }

    private fun updatePassword(newPassword: String) {
        updatePasswordUseCase(newPassword).onEach {
            when(it) {
                is Resource.Loading -> {
                    _passwordUpdateState.value = passwordUpdateState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false

                    )
                }
                is Resource.Success -> {
                    _passwordUpdateState.value = passwordUpdateState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }
                is Resource.Error -> {
                    _passwordUpdateState.value = passwordUpdateState.value.copy(
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