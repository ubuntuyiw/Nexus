package com.ubuntuyouiwe.nexus.presentation.create_chat_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.use_case.GetRolesUseCase
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateChatRoomViewModel @Inject constructor(
    private val getRolesUseCase: GetRolesUseCase

) : ViewModel() {

    private val _rolesState = mutableStateOf<RolesState>(RolesState())
    val rolesState: State<RolesState> = _rolesState

    init {
        getRoles()
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

}