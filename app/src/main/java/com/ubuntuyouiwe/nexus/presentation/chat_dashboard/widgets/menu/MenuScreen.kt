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
        MenuItem(icon = R.drawable.premium, text = "Premium", type = MenuItemType.PREMIUM),
        MenuItem(icon = R.drawable.settings, text = "Settings", type = MenuItemType.SETTINGS),
        MenuItem(icon = R.drawable.privacy_policy, text = "Privacy Policy", type = MenuItemType.PRIVACY_POLICY),
        MenuItem(icon = R.drawable.terms_of_use, text = "Terms of Use", type = MenuItemType.TERMS_OF_USE),
        MenuItem(icon = R.drawable.playstore, text = "Rate Us", type = MenuItemType.RATE_USE),
        MenuItem(icon = R.drawable.trash, text = "Trash Bin", type = MenuItemType.TRASH_BIN),
        MenuItem(icon = R.drawable.assist, text = "Help Center", type = MenuItemType.HELP_CENTER),
        MenuItem(icon = R.drawable.log_out, text = "Sign out", type = MenuItemType.SIGN_OUT),


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
                    Icon(imageVector = ImageVector.vectorResource(id = item.icon), contentDescription = item.text)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = item.text, style = MaterialTheme.typography.bodyLarge)

                }
            }
        }


    }

}