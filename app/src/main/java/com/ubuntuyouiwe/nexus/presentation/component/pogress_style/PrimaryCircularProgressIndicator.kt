package com.ubuntuyouiwe.nexus.presentation.component.pogress_style

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.ui.theme.LightGray

@Composable
fun PrimaryCircularProgressIndicator() {
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )

    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
            color = MaterialTheme.colorScheme.onSecondary,
            strokeCap = StrokeCap.Square,
            strokeWidth = 3.dp)
    }

}