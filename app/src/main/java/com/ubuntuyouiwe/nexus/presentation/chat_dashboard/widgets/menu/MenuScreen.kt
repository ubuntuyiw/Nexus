package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R

@Composable
fun MenuScreen(
    onClick: (MenuItemType) -> Unit
) {
    val menuItem = listOf<MenuItem>(
        MenuItem(icon = ImageVector.vectorResource(R.drawable.premium) , text = MenuItemType.PREMIUM.menuName, type = MenuItemType.PREMIUM),
        MenuItem(icon = Icons.Default.Build, text = MenuItemType.SETTINGS.menuName, type = MenuItemType.SETTINGS),
        MenuItem(icon = ImageVector.vectorResource(R.drawable.privacy_policy), text = MenuItemType.PRIVACY_POLICY.menuName, type = MenuItemType.PRIVACY_POLICY),
        MenuItem(icon = ImageVector.vectorResource(R.drawable.terms_of_use), text = MenuItemType.TERMS_OF_USE.menuName, type = MenuItemType.TERMS_OF_USE),
        MenuItem(icon = Icons.Default.StarRate, text = MenuItemType.RATE_US.menuName, type = MenuItemType.RATE_US),
        MenuItem(icon = Icons.Default.Archive, text = MenuItemType.ARCHIVED.menuName, type = MenuItemType.ARCHIVED),
        MenuItem(icon = Icons.Default.SupportAgent, text = MenuItemType.HELP_CENTER.menuName, type = MenuItemType.HELP_CENTER),
        MenuItem(icon = Icons.Default.Logout, text = MenuItemType.SIGN_OUT.menuName, type = MenuItemType.SIGN_OUT),
    )

    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        menuItem.forEach { item ->
            Box(modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick(item.type) }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()

                ) {
                    Icon(imageVector = item.icon, contentDescription = item.text)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = item.text, style = MaterialTheme.typography.bodyLarge)

                }
            }
        }


    }

}