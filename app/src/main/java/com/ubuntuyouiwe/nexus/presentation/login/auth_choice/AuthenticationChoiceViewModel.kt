package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.auth.GetGoogleSignInIntentUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.GoogleSignInUseCase
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthenticationChoiceViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    getGoogleSignInIntentUseCase: GetGoogleSignInIntentUseCase
) : ViewModel() {

    private val _googleSignInState = mutableStateOf(GoogleSignInState())
    val googleSignInState: State<GoogleSignInState> = _googleSignInState

    private val _googleSignInButtonState = mutableStateOf(ButtonState())
    val googleSignInButtonState: State<ButtonState> = _googleSignInButtonState

    init {
        _googleSignInState.value =
            googleSignInState.value.copy(intent = getGoogleSignInIntentUseCase())
    }

    private val url = "https://www.iubenda.com/privacy-policy/84531396"

    fun onEvent(event: AuthChoiceEvent) {
        when (event) {
            is AuthChoiceEvent.GoogleSignIn -> {
                handleGoogleSignIn(event.intent)
            }

            is AuthChoiceEvent.Email -> {
                event.navController.navigate(Screen.EMAIL_WITH_LOGIN.name)
            }


            is AuthChoiceEvent.PrivacyPolicy -> {
                event.onNavigate(url)
            }
        }

    }

    fun googleSignInCheckAndStart(result: ActivityResult) {
        if ((result.resultCode == Activity.RESULT_OK) && result.data != null)
            onEvent(AuthChoiceEvent.GoogleSignIn(result.data!!))
    }


    private fun handleGoogleSignIn(intent: Intent) {
        googleSignInUseCase(intent).onEach {
            when (it) {
                is Resource.Success -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        isSuccess = true,
                        isLoading = false,
                        isError = false
                    )
                    _googleSignInButtonState.value =
                        googleSignInButtonState.value.copy(enabled = true)
                }

                is Resource.Loading -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        isSuccess = false,
                        isLoading = true,
                        isError = false
                    )
                    _googleSignInButtonState.value =
                        googleSignInButtonState.value.copy(enabled = false)

                }

                is Resource.Error -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        isSuccess = false,
                        isLoading = false,
                        isError = true,
                        errorMessage = it.message
                    )
                    _googleSignInButtonState.value =
                        googleSignInButtonState.value.copy(enabled = true)
                }

            }
        }.launchIn(viewModelScope)
    }

}