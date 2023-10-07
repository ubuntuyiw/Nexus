package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R

@Composable
fun OrderVerificationItem(
    title: String,
    count: String,
    consumeOnClick: () ->Unit
) {
    val countText = stringResource(id = R.string.count)
    val consumeText = stringResource(id = R.string.consume)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column {
                Text(text = title)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "$countText: $count", style = MaterialTheme.typography.bodySmall)
            }

            Button(onClick = consumeOnClick) {
                Text(text = consumeText, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}