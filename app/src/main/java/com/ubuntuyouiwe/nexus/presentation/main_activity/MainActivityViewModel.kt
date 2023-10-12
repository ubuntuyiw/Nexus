package com.ubuntuyouiwe.nexus.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.use_case.auth.AuthStateUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.GetUserMessagingDataUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.GetUserUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SaveSystemLanguageUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.SignOutUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.token.GetTokenUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.token.RemoveTokenFromDatabaseUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.auth.token.SaveTokenUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.proto.settings.GetSettingsUseCase
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.SignOutState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase,
    sharedState: SharedState,
    private val getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val userMessagingDataUseCase: GetUserMessagingDataUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveSystemLanguageUseCase: SaveSystemLanguageUseCase,
) : ViewModel() {

    private val _userOperationState = mutableStateOf(UserOperationState())
    val userOperationState: State<UserOperationState> = _userOperationState

    private val _stateLogOut = mutableStateOf(SignOutState())
    val stateLogOut: State<SignOutState> = _stateLogOut

    private val _settingsState = sharedState.settings
    val settingsState: State<SettingsState> = _settingsState

    private val _userState = sharedState.userState
    val userState: State<UserOperationState> = _userState

    private val _userMessagingDataState = sharedState.userMessagingDataState

    private val _authListenerRetryButton = mutableStateOf(ButtonState())
    val authListenerRetryButton: State<ButtonState> = _authListenerRetryButton

    private val _getTokenState = sharedState.token
    val getTokenState: State<GetTokenState> = _getTokenState

    private val _saveTokenState = mutableStateOf(SaveTokenState())
    val saveTokenState: State<SaveTokenState> = _saveTokenState

    var startDestination =  mutableStateOf(Screen.SPLASH)

    var getUserJob: Job? = null
    var getUserMessagingJob: Job? = null
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.Retry -> {
                getAuthStateListener()
            }
        }
    }

    init {
        getToken()
        getSettings()
        getAuthStateListener()
    }

    fun getUserMessagingData(id: String) {
        getUserMessagingJob?.cancel()
        getUserMessagingJob = userMessagingDataUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _userMessagingDataState.value = _userMessagingDataState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _userMessagingDataState.value = _userMessagingDataState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        successData = resource.data
                    )
                }
                is Resource.Error -> {
                    _userMessagingDataState.value = _userMessagingDataState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(id: String) {
        getUserJob?.cancel()
        getUserJob = getUserUseCase(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _userState.value = userState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _userState.value = userState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        successData = resource.data
                    )
                    userState.value.successData?.let {
                        if (it.isOnBoarding) startDestination.value = Screen.UserName
                        else startDestination.value = Screen.CHAT_DASHBOARD
                    }
                }
                is Resource.Error -> {
                    _userState.value = userState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSettings() {
        getSettingsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    ) }

                is Resource.Success -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        successData = resource.data ?: Settings()
                    )
                }

                is Resource.Error -> {
                    _settingsState.value = settingsState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    fun logOut(token: String) {
        signOutUseCase(token).onEach {
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

    private fun getAuthStateListener() {
        authStateUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = false,
                        isError = false,
                        isLoading = true
                    )
                    startDestination.value = Screen.SPLASH
                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = false)
                }

                is Resource.Success -> {
                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = true,
                        successData = resource.data,
                        isError = false,
                        isLoading = false
                    )

                    userOperationState.value.successData?.let {
                        startDestination.value = Screen.CHAT_DASHBOARD

                    } ?: run {
                        startDestination.value = Screen.AUTHENTICATION_CHOICE
                    }

                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = true)
                }

                is Resource.Error -> {
                    _userOperationState.value = userOperationState.value.copy(
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message,
                        isLoading = false
                    )
                    _authListenerRetryButton.value =
                        authListenerRetryButton.value.copy(enabled = true)
                }



            }
        }.launchIn(viewModelScope)
    }


    private fun getToken() {
        getTokenUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _getTokenState.value = getTokenState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _getTokenState.value = getTokenState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        successData = it.data?: ""
                    )
                }
                is Resource.Error -> {
                    _getTokenState.value = getTokenState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    fun saveToken(token: String) {
        saveTokenUseCase(token).onEach {
            when (it) {
                is Resource.Loading -> {
                    _saveTokenState.value = saveTokenState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _saveTokenState.value = saveTokenState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }
                is Resource.Error -> {
                    _saveTokenState.value = saveTokenState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    fun saveSystemLanguage(code: String) {
        saveSystemLanguageUseCase(code).onEach {

        }.launchIn(viewModelScope)
    }

}