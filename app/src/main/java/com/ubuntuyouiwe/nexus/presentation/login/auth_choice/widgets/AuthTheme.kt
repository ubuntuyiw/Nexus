package com.ubuntuyouiwe.nexus.presentation.login.auth_choice.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.util.ThemeCategory

@Composable
fun AuthTheme(
    dropdownMenuState: Boolean,
    dropdownMenuStateChange: (Boolean) -> Unit,
    onClick: (Int) -> Unit
) {
    val short = ThemeCategory.values()
    val theme = stringResource(id = R.string.theme)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            dropdownMenuStateChange(true)
        }
    ) {
        Icon(imageVector = Icons.Default.DarkMode, contentDescription = Icons.Default.DarkMode.name)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = theme, style = MaterialTheme.typography.bodyLarge)
        DropdownMenu(
            expanded = dropdownMenuState,
            onDismissRequest = { dropdownMenuStateChange(false) },
            modifier = Modifier.background(MaterialTheme.colorScheme.scrim)
        ) {
            short.forEach { shortDate ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = shortDate.icon,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription =shortDate.icon.name
                        )
                    },
                    text = {
                        Text(
                            text = shortDate.field,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }, onClick = {
                        dropdownMenuStateChange(false)
                        onClick(shortDate.ordinal)
                    }

                )
            }
        }
    }


}