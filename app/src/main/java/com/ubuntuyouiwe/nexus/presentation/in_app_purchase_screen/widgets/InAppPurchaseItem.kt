package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter.Chapter

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InAppPurchaseItem(
    title: String,
    description: String,
    price: String,
    discount: String,
    message: String,
    buyOnclick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable {
                buyOnclick()
            }
            .fillMaxWidth()

    ) {

        Chapter(
            top = {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize()

                        ) {
                            GlideImage(
                                model = R.drawable.buy,
                                contentDescription = "Buy Icon",
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }


                    }
                }
            },
            bottom = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    if (message.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GlideImage(
                                model = R.drawable.star_product,
                                contentDescription = "star product",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.padding(start = 8.dp))
                            Text(
                                text = message,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.inversePrimary
                            )
                        }

                    }
                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = description.replace("\n",""),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Price: $price",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.padding(start = 28.dp, top = 8.dp))
                        Text(
                            text = "Discount: %$discount",
                            style = MaterialTheme.typography.bodyLarge
                        )

                    }


                }


            }
        )


    }
}