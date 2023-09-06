package com.ubuntuyouiwe.nexus.presentation.create_chat_room


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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateChatRoomScreen(navController: NavHostController, roleState: RolesState) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Who Would You Like to Talk With?",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = Icons.Default.Close.name,
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                    }
                }
            )
        }
    ) {
        val state = rememberScrollState()
        LazyColumn(modifier = Modifier
            .padding(it)) {
            items(roleState.data) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .clickable {
                            navController.navigateUp()
                            navController.navigate(Screen.MessagingPanel.name + "/${it.type}/${null}")

                        }
                        .fillMaxWidth()


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GlideImage(model = it.image, contentDescription = it.name.EN, modifier = Modifier.size(75.dp))
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(text = it.name.EN, style = MaterialTheme.typography.bodySmall)
                        }

                        Spacer(modifier = Modifier.padding(start = 16.dp))
                        Text(
                            text = it.description["EN"] ?:"",
                            style = MaterialTheme.typography.labelMedium
                        )

                    }

                }
            }
        }
    }
}