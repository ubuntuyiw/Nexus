package com.ubuntuyouiwe.nexus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexusTheme {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NexusTheme {

    }
}