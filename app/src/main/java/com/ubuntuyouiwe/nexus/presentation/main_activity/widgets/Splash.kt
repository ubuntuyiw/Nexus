package com.ubuntuyouiwe.nexus.presentation.main_activity.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUp
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.SignUpState
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme

@Composable
fun Splash() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }

}

@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun SplashPreview() {
    NexusTheme {
        Splash()
    }
}