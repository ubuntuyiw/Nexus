package com.ubuntuyouiwe.nexus.presentation.main_activity.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme

@Composable
fun UserOperationErrorUi(
    errorMessage: String,
    authListenerRetryButtonState: ButtonState,
    againOnClick: () -> Unit
) {
    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()

        ) {
            Text(text = errorMessage, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.padding(16.dp))
            PrimaryButton(onClick = againOnClick, enabled = authListenerRetryButtonState.enabled) {
                Text(text = "Retry", style = MaterialTheme.typography.bodyMedium)
            }


        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun UserOperationErrorUiPreview() {
    NexusTheme {
        val authListenerRetryButtonState = ButtonState()
        UserOperationErrorUi(
            "Error",
            authListenerRetryButtonState,
        ) {}
    }
}