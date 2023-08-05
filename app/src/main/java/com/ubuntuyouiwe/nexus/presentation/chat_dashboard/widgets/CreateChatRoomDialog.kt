package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper

@Composable
fun CreateChatRoomDialog() {

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

            Column(
                modifier = Modifier
                    .verticalScroll(state)
                    .fillMaxWidth()
            ) {
                repeat(20) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                            .shadow(
                                shape = RoundedCornerShape(16.dp),
                                elevation = 0.dp,
                                clip = true
                            )
                            .border(BorderStroke(2.dp, Whisper), RoundedCornerShape(16.dp))
                            .clickable {  }
                            .fillMaxWidth()

                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(painter = painterResource(id = R.drawable.debate_battle), contentDescription ="", modifier = Modifier.size(100.dp) )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = "Debate Battle", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(modifier = Modifier.padding(start = 16.dp))
                            Text(
                                text = stringResource(id = R.string.debateBattle),
                                style = MaterialTheme.typography.labelMedium
                            )

                        }

                    }
                }
            }

        }
    }


}