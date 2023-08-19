package com.ubuntuyouiwe.nexus.presentation.login.email_with_signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SignUpUseCase
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmailWithSignUpViewModel @Inject constructor(
    private val singUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    private val _signUpButtonState = mutableStateOf(ButtonState())
    val signUpButtonState: State<ButtonState> = _signUpButtonState


    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> {
                signUp(
                    UserCredentials(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                )
            }

            is SignUpEvent.EnterEmail -> {
                _emailState.value = emailState.value.copy(text = event.value)
            }

            is SignUpEvent.ChangePasswordVisibility -> {
                _passwordState.value = passwordState.value.copy(
                    isVisibility = !passwordState.value.isVisibility
                )
            }

            is SignUpEvent.EnterPassword -> {
                _passwordState.value = passwordState.value.copy(text = event.value)
            }
        }
    }

    private fun signUp(userCredentials: UserCredentials) {
        singUpUseCase(userCredentials).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _signUpState.value =
                        signUpState.value.copy(isSuccess = true, isError = false, isLoading = false)

                    _signUpButtonState.value = signUpButtonState.value.copy(enabled = true)
                }

                is Resource.Loading -> {
                    _emailState.value.isError = false
                    _passwordState.value.isError = false

                    _signUpState.value =
                        signUpState.value.copy( isLoading = true, isSuccess = false, isError = false,)

                    _signUpButtonState.value = signUpButtonState.value.copy(enabled = false)
                }

                is Resource.Error -> {
                    resource.errorCode?.let {
                        _emailState.value.isError = (it == ErrorCodes.ERROR_INVALID_EMAIL.name) ||
                                (it == ErrorCodes.ERROR_EMAIL_ALREADY_IN_USE.name)

                        _passwordState.value.isError = it == ErrorCodes.ERROR_WEAK_PASSWORD.name
                    } ?: run {
                        _emailState.value.isError = false
                        _passwordState.value.isError = false
                    }

                    _signUpState.value =
                        signUpState.value.copy(
                            isSuccess = false,
                            isError = true,
                            errorMessage = resource.message,
                            isLoading = false
                        )

                    _signUpButtonState.value = signUpButtonState.value.copy(enabled = true)
                }

            }
        }.launchIn(viewModelScope)

    }

}