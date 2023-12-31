package com.ubuntuyouiwe.nexus.presentation.component.snacbar_style

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.SoftGray

@Composable
fun PrimarySnackbar(
    modifier: Modifier = Modifier,
    snackbarData: SnackbarData,
    actionOnNewLine: Boolean = false
) {

    Snackbar(
        modifier = modifier,
        snackbarData = snackbarData,
        shape = RoundedCornerShape(32.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.primary,
        actionColor =MaterialTheme.colorScheme.primary,
        actionOnNewLine = actionOnNewLine,
        actionContentColor = MaterialTheme.colorScheme.primary,
        dismissActionContentColor = MaterialTheme.colorScheme.primary,
    )
}