package com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.SecondaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun EmailWithLoginTopBar(
    onClick: () -> Unit
) {
    val login = stringResource(id = R.string.login)
    PrimaryTopAppBar(
        title = {
            Text(
                text = login,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onClick,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = Icons.Default.ArrowBack.name,
                )
            }
        },
    )
}