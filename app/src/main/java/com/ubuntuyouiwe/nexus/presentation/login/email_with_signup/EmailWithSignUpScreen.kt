package com.ubuntuyouiwe.nexus.presentation.login.email_with_signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.PrimaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.SecondaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.TertiaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryHintText
import com.ubuntuyouiwe.nexus.presentation.component.top_app_bar_style.PrimaryTopAppBar
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.widgets.EmailWithSignUpTopBar
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetAnnotatedTermsAndPrivacyText
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetLoginSuggestionText
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun EmailWithSignUp(
    navController: NavController,
    signUpState: SignUpState,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    signUpButtonState: ButtonState,
    onEvent: (SignUpEvent) -> Unit
) {


    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    LaunchedEffect(key1 = signUpState) {
        if (signUpState.isSuccess) {
            hostState.showSnackbar(context.resources.getString(R.string.creation_was_successful))

        } else if (signUpState.isLoading) {
            hostState.showSnackbar(
                message = context.resources.getString(R.string.loading),
                duration = SnackbarDuration.Indefinite
            )

        } else if (signUpState.isError) {
            hostState.showSnackbar(
                message = signUpState.errorMessage,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

        }
    }
    val emailStateFocusRequester = remember { emailState.focusRequester }
    val passwordStateFocusRequester = remember { passwordState.focusRequester }

    LaunchedEffect(key1 = emailState.isError) {
        if (emailState.isError) {
            emailStateFocusRequester.requestFocus()
        }
    }
    LaunchedEffect(key1 = passwordState.isError) {
        if (passwordState.isError) {
            passwordStateFocusRequester.requestFocus()
        }
    }

    Scaffold(
        containerColor = White,
        topBar = {
            EmailWithSignUpTopBar {
                navController.navigateUp()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState) { data ->
                PrimarySnackbar(
                    snackbarData = data,
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
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
                    .fillMaxWidth()
                    .weight(90f)
            ) {

                PrimaryTextField(
                    value = emailState.text,
                    onValueChange = { onEvent(SignUpEvent.EnterEmail(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    isError = emailState.isError,
                    label = { PrimaryHintText(stringResource(id = R.string.email)) },
                    leadingIcon = {
                        TertiaryIcon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = stringResource(id = R.string.email),
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .focusRequester(emailStateFocusRequester)
                )

                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryTextField(
                    value = passwordState.text,
                    onValueChange = { onEvent(SignUpEvent.EnterPassword(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    isError = passwordState.isError,
                    label = { PrimaryHintText(stringResource(id = R.string.password)) },
                    leadingIcon = {
                        TertiaryIcon(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = stringResource(id = R.string.password),
                        )
                    },
                    trailingIcon = {
                        PrimaryIconButton(onClick = {
                            onEvent(SignUpEvent.ChangePasswordVisibility)
                        }) {
                            PrimaryIcon(
                                painter = painterResource(
                                    id = if (passwordState.isVisibility) R.drawable.dont_see
                                    else R.drawable.to_see
                                ),
                                contentDescription = stringResource(id = R.string.passwordVisibility),
                            )
                        }
                    },
                    visualChar = if (passwordState.isVisibility) null else '*',
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .focusRequester(passwordStateFocusRequester)
                )


                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryButton(
                    onClick = { onEvent(SignUpEvent.SignUp) },
                    enabled = signUpButtonState.enabled,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(36.dp),

                    ) {
                    Text(text = stringResource(id = R.string.SignUp), style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.padding(16.dp))

                GetLoginSuggestionText {
                    navController.navigateUp()
                }

                Spacer(modifier = Modifier.padding(16.dp))

            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(10f)
            ) {
                GetAnnotatedTermsAndPrivacyText(
                    termsOfUseOnClick = {

                    },
                    privacyPolicy = {

                    }
                )
            }


        }

    }
}


@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun EmailWithSignUpPreview() {
    val navController = rememberNavController()

    NexusTheme {
        val emailState = TextFieldState("ibrahim.kurt@ubuntuyouiwe.com", isVisibility = false)
        val passwordState = TextFieldState()
        val signUpState = SignUpState()
        val signUpButtonState = ButtonState()
        EmailWithSignUp(
            navController,
            signUpState,
            emailState,
            passwordState,
            signUpButtonState
        ) {

        }
    }
}