package com.ubuntuyouiwe.nexus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ubuntuyouiwe.nexus.ui.theme.NexusTheme

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