package com.ubuntuyouiwe.nexus.presentation.login.email_with_signup

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.widgets.EmailWithSignUpTopBar
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetAnnotatedTermsAndPrivacyText
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetLoginSuggestionText
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme

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

    val creationSuccessful = stringResource(id = R.string.creation_successful)
    val loading = stringResource(id = R.string.loading)
    val signup = stringResource(id = R.string.signup)
    val email = stringResource(id = R.string.email)
    val password = stringResource(id = R.string.password)
    val passwordVisibility = stringResource(id = R.string.password_visibility)

    LaunchedEffect(key1 = signUpState) {
        if (signUpState.isSuccess) {
            hostState.showSnackbar(creationSuccessful)

        } else if (signUpState.isLoading) {
            hostState.showSnackbar(
                message = loading,
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
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
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
                    label = {
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = email,
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
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
                    label = {
                        Text(
                            text = password,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = password
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            onEvent(SignUpEvent.ChangePasswordVisibility)
                        }) {
                            Icon(
                                imageVector =   if (passwordState.isVisibility) Icons.Default.Password
                                else Icons.Default.RemoveRedEye,
                                contentDescription = passwordVisibility,
                            )
                        }
                    },
                    visualChar = if (passwordState.isVisibility) null else '*',
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
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
                    Text(text = signup, style = MaterialTheme.typography.bodyMedium)
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
                        navController.navigate(Screen.TermsOfUseScreen.name)
                    },
                    privacyPolicy = {
                        val link = "https://www.iubenda.com/privacy-policy/84531396"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        context.startActivity(intent)
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