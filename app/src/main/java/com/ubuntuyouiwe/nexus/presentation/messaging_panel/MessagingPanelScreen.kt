package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Whisper

@Composable
fun MessagingPanelScreen() {

    Scaffold(
        containerColor = Whisper
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {


            val arrayList = ArrayList<Int>()
            repeat(5000) {
                arrayList.add(5)
            }


            LazyColumn() {
                items(arrayList) {
                    Column(modifier = Modifier.fillMaxSize(0.5f)) {
                        Text(text = "merhaba")
                    }

                }

            }

        }

    }
}