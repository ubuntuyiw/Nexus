package com.ubuntuyouiwe.nexus.presentation.onboarding.system_message

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemMessageScreen(
    navController: NavHostController,
    isAuto: Boolean,
    systemMessage: String,
    updateSystemMessageState: UpdateSystemMessageState,
    onEvent: (event: SystemMessageEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
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
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }
                },
                title = {
                    Text(
                        text = "Introduce Yourself to Nexus",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    if (updateSystemMessageState.isLoading) {
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
                        onEvent(SystemMessageEvent.UpdateSystemMessage)
                        if (!isAuto) {
                            navController.navigateUp()
                        }


                    },
                    enabled = systemMessage.length <= 500,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = if (isAuto) "Finish" else "Save",
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
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(scrollState)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "You have the freedom to personalize your Nexus experience. Share details about yourself to receive more tailored interactions. \nExamples:\n\n-Please address me in a formal tone.\n-I specialize in Artificial Intelligence Engineering.\n-I reside in the United States.\n-Traveling is my passion.",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                if (systemMessage.length > 500) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "Text must be under 500 characters",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                PrimaryTextField(
                    value = systemMessage,
                    onValueChange = {
                        onEvent(SystemMessageEvent.UserNameEnter(it))
                    },
                    isError = systemMessage.length > 500,
                    label = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Personalize Your Experience",
                                style = MaterialTheme.typography.labelLarge
                            )
                            Text(
                                text = "${systemMessage.length}/500",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp)
                )

            }
        }
    }

}