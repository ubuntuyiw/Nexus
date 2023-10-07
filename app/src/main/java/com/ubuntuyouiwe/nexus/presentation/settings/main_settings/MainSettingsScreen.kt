package com.ubuntuyouiwe.nexus.presentation.settings.main_settings

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.main_activity.SettingsState
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.settings.main_settings.widgets.SettingsArea
import com.ubuntuyouiwe.nexus.presentation.util.ThemeCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSettingsScreen(
    navController: NavHostController,
    useState: UserOperationState,
    settingsState: SettingsState,
    onEvent: (event: MainSettingsEvent) -> Unit
) {
    val settings = stringResource(id = R.string.settings)
    val nameString = stringResource(id = R.string.name)
    val emailString = stringResource(id = R.string.email)
    val password = stringResource(id = R.string.password)
    val personalizeExperience = stringResource(id = R.string.personalize_experience)
    val appUseIntent = stringResource(id = R.string.app_use_intent)
    val theme = stringResource(id = R.string.theme)

    val name = useState.successData?.name?: ""
    val email = useState.successData?.email?: ""

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
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }
                },
                title = {
                    Text(
                        text = settings,
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
                    title = nameString,
                    content = name,
                    isDetail = true,
                    isContent = true
                ) {
                    navController.navigate(Screen.UserName.name + "/${false}")

                }
                SettingsArea(
                    title = emailString,
                    content = email,
                    isDetail = false,
                    isContent = true,
                    onClick = {}
                )
                SettingsArea(
                    title = password,
                    content = "********",
                    isDetail = true,
                    isContent = true
                ) {
                    navController.navigate(Screen.Password.name)
                }

                SettingsArea(
                    title = personalizeExperience,
                    content = "",
                    isDetail = true,
                    isContent = false
                ) {
                    navController.navigate(Screen.SystemMessage.name + "/${false}")

                }

                SettingsArea(
                    title = appUseIntent,
                    content = "",
                    isDetail = true,
                    isContent = false
                ) {
                    navController.navigate(Screen.PurposeSelection.name + "/${false}")


                }

                val short = ThemeCategory.values()
                var dropdownMenuState by remember {
                    mutableStateOf(false)
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        dropdownMenuState = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)


                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = theme,
                                style = MaterialTheme.typography.bodyMedium
                            )

                        }
                        DropdownMenu(
                            expanded = dropdownMenuState,
                            onDismissRequest = { dropdownMenuState = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.scrim)
                        ) {
                            short.forEach { shortDate ->
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            imageVector = shortDate.icon,
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            contentDescription =shortDate.icon.name
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = shortDate.field,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }, onClick = {
                                        dropdownMenuState = false
                                        onEvent(MainSettingsEvent.ChangeMainSettings(shortDate.ordinal))
                                    }

                                )
                            }
                        }



                    }
                }

            }

        }

    }
}