package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.PrimaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.SecondaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.TertiaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun EmailWithLoginScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }


    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = White,
        topBar = {
            PrimaryTopAppBar(
                title = {
                    Text(text = "Login", style = MaterialTheme.typography.headlineSmall)
                },
                navigationIcon = {
                    PrimaryIconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        SecondaryIcon(
                            painter = painterResource(id = R.drawable.left_arrow),
                            contentDescription = "Left arrow"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxSize()
            ) {

                PrimaryTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "Email") },
                    leadingIcon = {
                        TertiaryIcon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "Password",
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Password") },
                    leadingIcon = {
                        TertiaryIcon(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = "Password",
                        )
                    },
                    trailingIcon = {
                        PrimaryIconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            PrimaryIcon(
                                painter = painterResource(
                                    id = if (passwordVisibility) R.drawable.dont_see
                                    else R.drawable.to_see
                                ),
                                contentDescription = "passwordVisibility",
                            )
                        }
                    },
                    visualChar = if (passwordVisibility) null else "*",
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )


                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),

                    ) {
                    Text(text = "Login")
                }


                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryClickableText(
                    text = "Don't have an account? ",
                    clickText = "Signup",
                    clickable = {
                        navController.navigate(Screen.EMAIL_WITH_SIGNUP.name)
                    },
                )
            }


        }

    }


}