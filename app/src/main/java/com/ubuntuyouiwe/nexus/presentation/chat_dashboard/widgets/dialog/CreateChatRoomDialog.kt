package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.RolesState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateChatRoomDialog(
    roles: RolesState,
    onClick: (Role) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        val state = rememberScrollState()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Who Would You Like to Talk With?",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(roles.data) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                            .shadow(
                                shape = RoundedCornerShape(16.dp),
                                elevation = 0.dp,
                                clip = true
                            )
                            .border(BorderStroke(2.dp, Whisper), RoundedCornerShape(16.dp))
                            .clickable { onClick(it) }
                            .fillMaxWidth()

                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            
                        }
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                GlideImage(model = it.image, contentDescription = it.name.EN, modifier = Modifier.size(100.dp))
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = it.name.EN ?:"", style = MaterialTheme.typography.bodySmall)
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


}