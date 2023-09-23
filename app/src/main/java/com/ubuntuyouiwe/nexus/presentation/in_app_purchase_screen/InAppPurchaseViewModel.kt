package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.domain.use_case.billing.BillingClientBuildUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.ConnectionResultUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.ConsumeTheProductUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.EndConnectionUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.IsReadyUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.PurchasesUpdateUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.QueryProductDetailsUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.QueryPurchasesUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.StartConnectionUseCase
import com.ubuntuyouiwe.nexus.domain.use_case.billing.TriggerQueryPurchasesUseCase
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ConsumeState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.QueryPurchaseState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.PurchasesUpdateState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ConnectionState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ProductDetailsState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserMessagingDataState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.state.SharedState
import com.ubuntuyouiwe.nexus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class InAppPurchaseViewModel @Inject constructor(
    private val sharedState: SharedState,
    private val startConnectionUseCase: StartConnectionUseCase,
    private val connectionResultUseCase: ConnectionResultUseCase,
    private val queryProductDetailsUseCase: QueryProductDetailsUseCase,
    private val purchasesUpdateUseCase: PurchasesUpdateUseCase,
    private val billingClientBuildUseCase: BillingClientBuildUseCase,
    private val consumeTheProductUseCase: ConsumeTheProductUseCase,
    private val queryPurchasesUseCase: QueryPurchasesUseCase,
    private val triggerQueryPurchasesUseCase: TriggerQueryPurchasesUseCase,
    private val endConnectionUseCase: EndConnectionUseCase,
    private val isReadyUseCase: IsReadyUseCase
) : ViewModel() {


    private val _queryPurchaseState = mutableStateOf(QueryPurchaseState())
    val queryPurchaseState: State<QueryPurchaseState> = _queryPurchaseState

    private val _consumeState = mutableStateOf(ConsumeState())
    val consumeState: State<ConsumeState> = _consumeState

    private val _userMessagingDataState = sharedState.userMessagingDataState
    val userMessagingDataState: State<UserMessagingDataState> = _userMessagingDataState

    private val _userState = sharedState.userState
    val userState: State<UserOperationState> = _userState

    private val _purchasesUpdateState = mutableStateOf(PurchasesUpdateState())
    val purchasesUpdateState: State<PurchasesUpdateState> = _purchasesUpdateState


    private val _connectionState = mutableStateOf(ConnectionState())
    val connectionState: State<ConnectionState> = _connectionState

    private val _productDetailsState = mutableStateOf(ProductDetailsState())
    val productDetailsState: State<ProductDetailsState> = _productDetailsState

    init {
        startConnectionUseCase()
        connectionResult()
        queryProductDetails()
        purchasesUpdated()
        getPurchases()

    }


    fun onEvent(event: InAppPurchaseEvent) {
        when (event) {
            is InAppPurchaseEvent.ConsumePurchase -> {
                consumePurchase(event.purchaseDto)
            }

            is InAppPurchaseEvent.BillingClientBuild -> {
                event.billingClientBuild(billingClientBuildUseCase())
            }
            is InAppPurchaseEvent.RetryConnection -> {
                startConnectionUseCase()
            }
        }
    }

    fun isReady() = isReadyUseCase()

    val triggerPurchasesQuery = flow {
        while (true) {
            emit(triggerQueryPurchasesUseCase())
            delay(1000)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private fun connectionResult() {
        connectionResultUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _connectionState.value = connectionState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _connectionState.value = connectionState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )

                }

                is Resource.Error -> {
                    _connectionState.value = connectionState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun queryProductDetails() {
        queryProductDetailsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _productDetailsState.value = productDetailsState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )

                }

                is Resource.Success -> {
                    _productDetailsState.value = productDetailsState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _productDetailsState.value = productDetailsState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun purchasesUpdated() {
        purchasesUpdateUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _purchasesUpdateState.value = purchasesUpdateState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _purchasesUpdateState.value = purchasesUpdateState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }

                is Resource.Error -> {
                    _purchasesUpdateState.value = purchasesUpdateState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun getPurchases() {
        queryPurchasesUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _queryPurchaseState.value = queryPurchaseState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }

                is Resource.Success -> {
                    _queryPurchaseState.value = queryPurchaseState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        data = resource.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _queryPurchaseState.value = queryPurchaseState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun consumePurchase(purchaseDto: PurchaseDto) {
        consumeTheProductUseCase(purchaseDto).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _consumeState.value = consumeState.value.copy(
                        isLoading = true,
                        isSuccess = false,
                        isError = false
                    )
                }


                is Resource.Success -> {
                    _consumeState.value = consumeState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false
                    )
                }

                is Resource.Error -> {
                    _consumeState.value = consumeState.value.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        errorMessage = resource.message
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        endConnectionUseCase()
    }


}