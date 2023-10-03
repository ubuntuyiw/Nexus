package com.ubuntuyouiwe.nexus.presentation.widgets.terms_of_use

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ubuntuyouiwe.nexus.domain.use_case.GetTermsOfUseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsOfUseViewModel @Inject constructor(
    private val getTermsOfUseUseCase: GetTermsOfUseUseCase
): ViewModel() {


    val getTermsOfUse = mutableStateOf(getTermsOfUseUseCase())
}