package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.use_case.auth.EmailSignInUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.PasswordResetUseCase
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state.ResetPasswordState
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state.SignInState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmailWithLogInViewModel @Inject constructor(
    private val passwordResetUseCase: PasswordResetUseCase,
    private val signInUseCase: EmailSignInUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _signInButtonState = mutableStateOf(ButtonState())
    val signInButtonState: State<ButtonState> = _signInButtonState

    private val _signInState = mutableStateOf(SignInState())
    val signInState: State<SignInState> = _signInState


    private val _passwordResetState = mutableStateOf(ResetPasswordState())
    val passwordResetState: State<ResetPasswordState> = _passwordResetState


    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EnterEmail -> {
                _emailState.value = emailState.value.copy(text = event.emailText)
            }

            is SignInEvent.Send -> {
                handlePasswordReset(emailState.value.text)
            }

            is SignInEvent.SignIn -> {
                handleSignIn(
                    UserCredentials(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                )
            }

            is SignInEvent.ChangePasswordVisibility -> {
                _passwordState.value =
                    passwordState.value.copy(isVisibility = !passwordState.value.isVisibility)
            }

            is SignInEvent.EnterPassword -> {
                _passwordState.value = passwordState.value.copy(text = event.passwordText)
            }

            is SignInEvent.ChangeDialogVisibility -> {
                _passwordResetState.value =
                    passwordResetState.value.copy(dialogVisibility = event.isVisibility)
            }

            is SignInEvent.NavController -> {
                emailState.value.focusRequester.freeFocus()
                event.navController(Screen.EMAIL_WITH_SIGNUP)
            }

        }
    }

    private fun handleSignIn(userCredentials: UserCredentials) {
        signInUseCase(userCredentials).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _signInState.value =
                        signInState.value.copy(isLoading = true, isSuccess = false, isError = false)

                    _signInButtonState.value = signInButtonState.value.copy(enabled = false)
                }

                is Resource.Error -> {
                    resource.errorCode?.let {
                        emailState.value.isError = (it == ErrorCodes.ERROR_INVALID_EMAIL.name) ||
                                (it == ErrorCodes.ERROR_EMAIL_ALREADY_IN_USE.name)

                        passwordState.value.isError = it == ErrorCodes.ERROR_WEAK_PASSWORD.name
                    } ?: run {
                        emailState.value.isError = false
                        passwordState.value.isError = false
                    }

                    _signInState.value = signInState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                    _signInButtonState.value = signInButtonState.value.copy(enabled = true)

                }

                is Resource.Success -> {
                    _signInState.value =
                        signInState.value.copy(isLoading = false, isSuccess = true, isError = false)
                    _signInButtonState.value = signInButtonState.value.copy(enabled = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun handlePasswordReset(email: String) {
        passwordResetUseCase(email).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _passwordResetState.value = passwordResetState.value.copy(
                        isLoading = false,
                        isErrorState = false,
                        isSuccess = true
                    )
                }

                is Resource.Loading -> {
                    _passwordResetState.value = passwordResetState.value.copy(
                        isLoading = true,
                        isErrorState = false,
                        isSuccess = false
                    )
                }

                is Resource.Error -> {
                    _passwordResetState.value = passwordResetState.value.copy(
                        isLoading = false,
                        isErrorState = true,
                        errorMessage = resource.message,
                        isSuccess = false
                    )
                }

            }
        }.launchIn(viewModelScope)
    }


}