package com.ubuntuyouiwe.nexus.presentation.component.fab_style

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun PrimaryFab(
    onClick: () -> Unit
) {
    val image: ImageVector = ImageVector.vectorResource(id = R.drawable.add_chat)

    ExtendedFloatingActionButton(
        text = { Text(text = "New Chat") },
        icon = { Icon(imageVector = image, contentDescription = "", modifier = Modifier.size(24.dp) )},
        onClick = onClick,
        expanded = false,
        containerColor = DeepBlueSea,
        contentColor = White
    )

}