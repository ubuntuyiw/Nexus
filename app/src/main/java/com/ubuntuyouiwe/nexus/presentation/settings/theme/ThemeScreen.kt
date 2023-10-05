package com.ubuntuyouiwe.nexus.presentation.settings.theme

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ubuntuyouiwe.nexus.presentation.main_activity.SettingsState

@Composable
fun ThemeScreen(
    settingsState: SettingsState,
    onEvent: (event: ThemeEvent) -> Unit
) {
    val short = ThemeCategory.values()



    Column {
        short.forEach { shortDate ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                RadioButton(
                    selected = shortDate.ordinal == settingsState.successData.theme,
                    onClick = {
                        onEvent(ThemeEvent.ChangeTheme(shortDate.ordinal))
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.onSurface,
                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
                Text(
                    text = shortDate.field,
                    style = MaterialTheme.typography.bodyMedium
                )

            }


        }
    }



}