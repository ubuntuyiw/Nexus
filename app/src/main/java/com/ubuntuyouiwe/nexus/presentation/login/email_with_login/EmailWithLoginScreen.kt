package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_button_style.PrimaryIconButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.PrimaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.TertiaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryHintText
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state.ResetPasswordState
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state.SignInState
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets.EmailWithLoginTopBar
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets.ForgotPasswordDialog
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetAnnotatedTermsAndPrivacyTextForLoggedInUser
import com.ubuntuyouiwe.nexus.presentation.login.widgets.PasswordForgetPrompt
import com.ubuntuyouiwe.nexus.presentation.login.widgets.SignUpPrompt
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DarkGray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme

@Composable
fun EmailWithLoginScreen(
    navController: NavController,
    signInState: SignInState,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    passwordResetState: ResetPasswordState,
    signInButtonState: ButtonState,
    onEvent: (SignInEvent) -> Unit
) {

    val hostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val login = stringResource(id = R.string.login)
    val loginSuccessful = stringResource(id = R.string.login_successful)
    val email = stringResource(id = R.string.email)
    val password = stringResource(id = R.string.password)
    val passwordVisibility = stringResource(id = R.string.password_visibility)


    LaunchedEffect(key1 = signInState) {
        if (signInState.isLoading) {
            hostState.showSnackbar(
                message = login,
                duration = SnackbarDuration.Indefinite
            )
        } else if (signInState.isError) {
            hostState.showSnackbar(
                message = signInState.errorMessage,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

        } else if (signInState.isSuccess) {
            hostState.showSnackbar(loginSuccessful)
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
            EmailWithLoginTopBar {
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
                    onValueChange = { onEvent(SignInEvent.EnterEmail(it)) },
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
                    onValueChange = { onEvent(SignInEvent.EnterPassword(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = password,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    isError = passwordState.isError,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = password
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            onEvent(SignInEvent.ChangePasswordVisibility)
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
                    onClick = {
                        onEvent(SignInEvent.SignIn)
                    },
                    enabled = signInButtonState.enabled,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(36.dp),

                    ) {
                    Text(
                        text = login,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                Spacer(modifier = Modifier.padding(16.dp))

                PasswordForgetPrompt {
                    onEvent(SignInEvent.ChangeDialogVisibility(true))
                }


                if (passwordResetState.dialogVisibility) {
                    Dialog(
                        onDismissRequest = { onEvent(SignInEvent.ChangeDialogVisibility(false)) },
                    ) {
                        ForgotPasswordDialog(
                            emailValue = emailState.text,
                            onValueChangeEmailValue = { onEvent(SignInEvent.EnterEmail(it)) },
                            approvalOnClick = {
                                onEvent(SignInEvent.Send)
                            },
                            rejectOnClick = {
                                onEvent(SignInEvent.ChangeDialogVisibility(false))
                            },
                            resetPasswordState = passwordResetState,

                            )
                    }
                }


                Spacer(modifier = Modifier.padding(8.dp))

                SignUpPrompt(
                    onClick = {

                        onEvent(SignInEvent.NavController {
                            navController.navigate(it.name)
                        })
                    }
                )

                Spacer(modifier = Modifier.padding(8.dp))


            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(10f)
            ) {
                GetAnnotatedTermsAndPrivacyTextForLoggedInUser(
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

@Preview(showBackground = true)
@Composable
fun EmailWithSignUpPreview() {
    val navController = rememberNavController()

    NexusTheme {
        val signInState = SignInState()
        val emailState = TextFieldState(text = "ibrahim.kurt")
        val passwordState = TextFieldState()
        val signInButtonState = ButtonState()
        val passwordResetState = ResetPasswordState(dialogVisibility = true)

        EmailWithLoginScreen(
            navController,
            signInState,
            emailState,
            passwordState,
            passwordResetState,
            signInButtonState
        ) {


        }
    }
}