package com.ubuntuyouiwe.nexus.presentation.settings.main_settings.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsArea(
    title: String,
    content: String,
    isDetail: Boolean,
    isContent: Boolean,
    onClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)


    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,

                    )
                if (isContent) {
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = content,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

            }
            if (isDetail) {
                Spacer(modifier = Modifier.padding(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowRight,
                    contentDescription = Icons.Default.ArrowRight.name,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }



        }
    }
}