package com.ubuntuyouiwe.nexus.presentation.settings.main_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.settings.main_settings.widgets.SettingsArea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSettingsScreen(navController: NavHostController, useState: UserOperationState) {
    val name = useState.successData?.name?: ""
    val email = useState.successData?.email?: ""

    Scaffold(
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
                        text = "Settings",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                }
            )
        },
    ) {paddingValues ->
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
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                SettingsArea(
                    title = "Name",
                    content = name,
                    isDetail = true,
                    isContent = true
                ) {
                    navController.navigate(Screen.UserName.name + "/${false}")

                }
                SettingsArea(
                    title = "Email",
                    content = email,
                    isDetail = false,
                    isContent = true,
                    onClick = {}
                )
                SettingsArea(
                    title = "Password",
                    content = "********",
                    isDetail = true,
                    isContent = true
                ) {
                    navController.navigate(Screen.Password.name)
                }

                SettingsArea(
                    title = "Personalize Your Experience",
                    content = "",
                    isDetail = true,
                    isContent = false
                ) {
                    navController.navigate(Screen.SystemMessage.name + "/${false}")

                }

                SettingsArea(
                    title = "App Use Intent",
                    content = "",
                    isDetail = true,
                    isContent = false
                ) {
                    navController.navigate(Screen.PurposeSelection.name + "/${false}")


                }
                SettingsArea(
                    title = "Theme",
                    content = "",
                    isDetail = true,
                    isContent = false
                ) {
                    navController.navigate(Screen.Theme.name)


                }

            }

        }

    }
}