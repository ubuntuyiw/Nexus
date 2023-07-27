package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryVariantButton
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun AuthenticationChoiceScreen(navController: NavController) {
    var value by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = White
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(50f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Nexus",
                    style = MaterialTheme.typography.displayLarge
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(40f)
            ) {

                PrimaryVariantButton(
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .height(55.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "google"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Google")
                }

                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryButton(
                    onClick = { navController.navigate(Screen.EMAIL_WITH_LOGIN.name) },
                    modifier = Modifier
                        .width(280.dp)
                        .height(50.dp),


                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = "email", tint = White
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Email")
                }

                Spacer(modifier = Modifier.padding(16.dp))


            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(10f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "From\nUBUNTUYOUIWE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
            }


        }
    }


}
