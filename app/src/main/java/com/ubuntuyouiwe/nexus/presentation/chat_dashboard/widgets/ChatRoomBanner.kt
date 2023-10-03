package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun ChatRoomBanner() {
    AndroidView(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.FULL_BANNER)
                adUnitId = "ca-app-pub-8437475970369583/1041390059"
                loadAd(AdRequest.Builder().build())
            }
        }
    )

}
