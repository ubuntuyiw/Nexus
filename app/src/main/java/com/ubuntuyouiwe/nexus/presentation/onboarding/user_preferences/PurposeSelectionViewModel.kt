package com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection
import com.ubuntuyouiwe.nexus.domain.use_case.auth.UpdatePurposeSelectionUseCase
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PurposeSelectionViewModel @Inject constructor(
    sharedState: SharedState,
    private val updatePurposeSelectionUseCase: UpdatePurposeSelectionUseCase
): ViewModel() {

    val getPurposeSelection = sharedState.userState

    private val _updatePurposeSelection = mutableStateOf(UpdatePurposeSelectionState())
    val updatePurposeSelection: State<UpdatePurposeSelectionState> = _updatePurposeSelection

    init {

    }

    fun onEvent(event: PurposeSelectionEvent) {
        when(event) {
            is  PurposeSelectionEvent.UpdatePurposeSelection -> {
                updatePurposeSelection(event.purposeSelection)
            }
        }

    }


    private fun updatePurposeSelection(purposeSelection: PurposeSelection) {
        updatePurposeSelectionUseCase(purposeSelection).onEach {
            when(it) {
                is Resource.Loading -> {
                    _updatePurposeSelection.value = updatePurposeSelection.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }
                is Resource.Success -> {
                    _updatePurposeSelection.value = updatePurposeSelection.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = it.data?: PurposeSelection()
                    )
                }
                is Resource.Error -> {
                    _updatePurposeSelection.value = updatePurposeSelection.value.copy(
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