package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.billingclient.api.BillingFlowParams
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ubuntuyouiwe.nexus.domain.util.ProductsFields
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ConsumeState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.PurchasesUpdateState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.QueryPurchaseState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ConnectionState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ProductDetailsState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets.InAppPurchaseItem
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets.LoadingDialog
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets.OrderVerificationScreen
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserMessagingDataState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun InAppPurchaseScreen(
    purchasesUpdateState: PurchasesUpdateState,
    connectionState: ConnectionState,
    productDetailsState: ProductDetailsState,
    queryPurchaseState: QueryPurchaseState,
    consumeState: ConsumeState,
    isReady: Boolean,
    userState: UserOperationState,
    userMessagingDataState: UserMessagingDataState,
    onEvent: (event: InAppPurchaseEvent) -> Unit
) {

    var orderVerificationScreenVisibility by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val hostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = connectionState) {
        if (connectionState.isError) {
            val result = hostState.showSnackbar(
                connectionState.errorMessage,
                actionLabel = "Retry",
                duration = SnackbarDuration.Indefinite
            )
            if (result == SnackbarResult.ActionPerformed) {
                onEvent(InAppPurchaseEvent.RetryConnection)
            }
        }
    }



    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState) {
                PrimarySnackbar(snackbarData = it)
            }
        },
        topBar = {
            TopAppBar(title = {
                Column {
                    Text(text = "Message Buy", style = MaterialTheme.typography.titleMedium)
                }

            },
                actions = {

                    IconButton(
                        onClick = { orderVerificationScreenVisibility = true },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = Icons.Default.Notifications.name,
                            )
                            if (queryPurchaseState.data.isNotEmpty()) {
                                Text(
                                    text = queryPurchaseState.data.size.toString(),
                                    fontSize = 11.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.TopStart)
                                        .background(MaterialTheme.colorScheme.error)
                                )
                            }

                        }
                    }
                })
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

            ) {
                this.stickyHeader {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background,)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Available Messages: " + userMessagingDataState.successData?.totalMessages.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }


                }

                items(productDetailsState.data) { productDetails ->


                    var discount = ""
                    var message = ""

                    when (ProductsFields.values().first { it.id == productDetails.productId }) {
                        ProductsFields.Message10 -> {
                            discount = "0"
                        }

                        ProductsFields.Message100 -> {
                            discount = "5"
                        }

                        ProductsFields.Message250 -> {
                            discount = "15"
                        }

                        ProductsFields.Message500 -> {
                            discount = "25"
                            message = "Best-Selling Package"
                        }

                        ProductsFields.Message1000 -> {
                            discount = "35"
                        }

                        ProductsFields.Message10000 -> {
                            discount = "50"
                            message = "Most Advantageous Package"
                        }
                    }


                    InAppPurchaseItem(
                        title = productDetails.name,
                        description = productDetails.description,
                        productDetails.oneTimePurchaseOfferDetails?.formattedPrice?:"",
                        discount,
                        message
                    ) {
                        val productDetailsParamsList = listOf(
                            BillingFlowParams.ProductDetailsParams
                                .newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                        )
                        val billingFlowParams = BillingFlowParams
                            .newBuilder()
                            .setProductDetailsParamsList(productDetailsParamsList)
                            .build()


                        onEvent(InAppPurchaseEvent.BillingClientBuild {
                            it.launchBillingFlow(
                                context as Activity,
                                billingFlowParams
                            )
                        })
                    }



                }


            }

        }



    }
    LaunchedEffect(key1 = productDetailsState) {
        if (productDetailsState.isError) {
            hostState.showSnackbar(productDetailsState.errorMessage)
        }
        if (purchasesUpdateState.isError) {
            hostState.showSnackbar(purchasesUpdateState.errorMessage)
        }

    }

    if (
        purchasesUpdateState.isLoading ||
        productDetailsState.isLoading
    ) {
        LoadingDialog()
    }


    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    if (orderVerificationScreenVisibility) {
        BackHandler {
            orderVerificationScreenVisibility = false
        }
    }
    AnimatedVisibility(
        visible = orderVerificationScreenVisibility,
        enter = slideInVertically(initialOffsetY = { screenHeight }) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically(targetOffsetY = { screenHeight }) + fadeOut(targetAlpha = 0.3f)
    ) {
        OrderVerificationScreen(
            queryPurchaseState = queryPurchaseState,
            consumeState,
            consumeOnClick = {
                onEvent(InAppPurchaseEvent.ConsumePurchase(it))

            },
        ) {
            orderVerificationScreenVisibility = false
        }
    }


}

