package com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.SecondaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar

@Composable
fun EmailWithLoginTopBar(
    onClick: () -> Unit
) {
    PrimaryTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            PrimaryIconButton(
                onClick = onClick,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                SecondaryIcon(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = stringResource(id = R.string.left_arrow)
                )
            }
        },
    )
}