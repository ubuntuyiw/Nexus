package com.ubuntuyouiwe.nexus.presentation.onboarding.user_name

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameScreen(
    navController: NavHostController,
    isAuto: Boolean,
    userName: String,
    updateDisplayNameState: UpdateDisplayNameState,
    onEvent: (event: UserNameEvent) -> Unit
) {
    LaunchedEffect(key1 = updateDisplayNameState) {

        if (updateDisplayNameState.isSuccess) {
            if (isAuto) {
                navController.navigate(Screen.PurposeSelection.name + "/${true}")
                onEvent(UserNameEvent.UpdateDisplayNameStateReset)
            } else {
                navController.navigateUp()
            }

        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    if (!isAuto) {
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
                    }
                },
                title = {
                    Text(text = "User Name", style = MaterialTheme.typography.titleMedium)
                },
                actions = {
                    if (updateDisplayNameState.isLoading) {
                        PrimaryCircularProgressIndicator()
                    }
                }
            )
        },
        bottomBar = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onEvent(UserNameEvent.UpdateUserName(userName))


                    },
                    enabled = userName.length <= 30,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {

                    Text(
                        text = if (isAuto) "Next" else "Save",
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                PrimaryTextField(
                    value = userName,
                    onValueChange = {
                        onEvent(UserNameEvent.UserNameEnter(it))
                    },
                    singleLine = true,
                    maxLines = 1,
                    isError = userName.length > 30,
                    label =  {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Name",
                                style = MaterialTheme.typography.labelLarge
                            )

                            Text(
                                text = "${userName.length}/30",
                                style = MaterialTheme.typography.labelLarge
                            )

                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if (updateDisplayNameState.isError) {
                    Text(
                        text = updateDisplayNameState.errorMessage,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "You can personalize your Nexus experience by entering your name.",
                    style = MaterialTheme.typography.labelLarge
                )
                if (userName.length > 30) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "Text must be under 30 characters",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
       

    }
}