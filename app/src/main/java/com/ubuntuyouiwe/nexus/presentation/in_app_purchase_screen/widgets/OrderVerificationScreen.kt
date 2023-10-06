package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ubuntuyouiwe.nexus.data.dto.billing.PurchaseDto
import com.ubuntuyouiwe.nexus.domain.util.ProductsFields
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ConsumeState
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.QueryPurchaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderVerificationScreen(
    queryPurchaseState: QueryPurchaseState,
    consumeState: ConsumeState,
    consumeOnClick: (PurchaseDto) -> Unit,
    orderVerificationScreenVisibility: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(text = "Untouched Products", style = MaterialTheme.typography.titleMedium)
                },
                navigationIcon = {
                    IconButton(onClick = orderVerificationScreenVisibility) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = Icons.Default.Close.name
                        )
                    }
                })
        },
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(queryPurchaseState.data) { purchaseDto ->
                var productName = ""

                when (ProductsFields.values().first { it.id == purchaseDto.products.first() }) {
                    ProductsFields.Message10 -> {
                        productName = "10 Message Pack"
                    }

                    ProductsFields.Message100 -> {
                        productName = "100 Message Pack"
                    }

                    ProductsFields.Message250 -> {
                        productName = "250 Message Pack"
                    }

                    ProductsFields.Message500 -> {
                        productName = "500 Message Pack"
                    }

                    ProductsFields.Message1000 -> {
                        productName = "1000 Message Pack"
                    }

                    ProductsFields.Message10000 -> {
                        productName = "10000 Message Pack"
                    }
                }
                OrderVerificationItem(
                    title = productName,
                    count = purchaseDto.quantity.toString()
                ) {
                    consumeOnClick(purchaseDto)
                }


            }

        }

    }
    if (queryPurchaseState.data.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You have no unused or unconsumed products.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    if (consumeState.isLoading) {
        LoadingDialog()
    }
}