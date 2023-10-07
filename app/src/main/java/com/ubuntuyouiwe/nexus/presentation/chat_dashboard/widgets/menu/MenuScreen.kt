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
import java.util.Locale

@Composable
fun MenuScreen(
    onClick: (MenuItemCategory) -> Unit
) {
    val systemLanguage = Locale.getDefault().language.uppercase(Locale.ROOT)

    val menuItem = listOf<MenuItem>(
        MenuItem(
            icon = ImageVector.vectorResource(R.drawable.premium),
            text = if (systemLanguage == "TR") MenuItemCategory.BUY_MESSAGES.menuNameTR else MenuItemCategory.BUY_MESSAGES.menuName,
            type = MenuItemCategory.BUY_MESSAGES
        ),
        MenuItem(
            icon = Icons.Default.Build,
            text = if (systemLanguage == "TR") MenuItemCategory.SETTINGS.menuNameTR else MenuItemCategory.SETTINGS.menuName,
            type = MenuItemCategory.SETTINGS
        ),
        MenuItem(
            icon = ImageVector.vectorResource(R.drawable.privacy_policy),
            text = if (systemLanguage == "TR") MenuItemCategory.PRIVACY_POLICY.menuNameTR else MenuItemCategory.PRIVACY_POLICY.menuName,
            type = MenuItemCategory.PRIVACY_POLICY
        ),
        MenuItem(
            icon = ImageVector.vectorResource(R.drawable.terms_of_use),
            text = if (systemLanguage == "TR") MenuItemCategory.TERMS_OF_USE.menuNameTR else MenuItemCategory.TERMS_OF_USE.menuName,
            type = MenuItemCategory.TERMS_OF_USE
        ),
        MenuItem(
            icon = Icons.Default.StarRate,
            text = if (systemLanguage == "TR") MenuItemCategory.RATE_US.menuNameTR else MenuItemCategory.RATE_US.menuName,
            type = MenuItemCategory.RATE_US
        ),
        MenuItem(
            icon = Icons.Default.Archive,
            text = if (systemLanguage == "TR") MenuItemCategory.ARCHIVED.menuNameTR else MenuItemCategory.ARCHIVED.menuName,
            type = MenuItemCategory.ARCHIVED
        ),
        MenuItem(
            icon = Icons.Default.SupportAgent,
            text = if (systemLanguage == "TR") MenuItemCategory.HELP_CENTER.menuNameTR else MenuItemCategory.HELP_CENTER.menuName,
            type = MenuItemCategory.HELP_CENTER
        ),
        MenuItem(
            icon = Icons.Default.Logout,
            text = if (systemLanguage == "TR") MenuItemCategory.SIGN_OUT.menuNameTR else MenuItemCategory.SIGN_OUT.menuName,
            type = MenuItemCategory.SIGN_OUT
        ),
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