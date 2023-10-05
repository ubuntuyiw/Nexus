package com.ubuntuyouiwe.nexus.presentation.settings.password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.state.TextFieldState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    navController: NavHostController,
    passwordTextFieldState: TextFieldState,
    passwordUpdateState: PasswordUpdateState,
    onEvent: (event: PasswordEvent) -> Unit,
) {
    val passwordStateFocusRequester = remember { passwordTextFieldState.focusRequester }
    val hostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = passwordUpdateState) {
        if (passwordUpdateState.isError) {
            hostState.showSnackbar(message = passwordUpdateState.errorMessage)
            passwordStateFocusRequester.requestFocus()
        }
        if (passwordUpdateState.isSuccess) {
            hostState.showSnackbar(message = "Successful")
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState) {
                Snackbar(snackbarData = it)
            }
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        }
                    ) {
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = Icons.Default.ArrowBack.name,
                            tint = White
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }
                },
                title = {
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    if (passwordUpdateState.isLoading) {
                        PrimaryCircularProgressIndicator()
                    }
                }
            )
        },
        bottomBar = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onEvent(PasswordEvent.ChangePassword(passwordTextFieldState.text))
                    },
                    enabled = passwordTextFieldState.text.length in 1..30,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {

                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                PrimaryTextField(
                    value = passwordTextFieldState.text,
                    onValueChange = { onEvent(PasswordEvent.ChangePasswordTextField(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    label = {
                        Text(
                            text = "New Password",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    isError = passwordTextFieldState.isError,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "New Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            onEvent(PasswordEvent.ChangePasswordVisibility)
                        }) {

                            Icon(
                                imageVector = if (passwordTextFieldState.isVisibility) Icons.Default.Password
                                else Icons.Default.RemoveRedEye,
                                contentDescription = stringResource(id = R.string.passwordVisibility),
                            )
                        }
                    },
                    visualChar = if (passwordTextFieldState.isVisibility) null else '*',
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .focusRequester(passwordStateFocusRequester)
                )

            }
        }

    }
}