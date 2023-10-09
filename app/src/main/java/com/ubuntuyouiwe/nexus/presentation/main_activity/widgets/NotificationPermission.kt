package com.ubuntuyouiwe.nexus.presentation.main_activity.widgets

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ubuntuyouiwe.nexus.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationPermission(
    hostState: SnackbarHostState
) {
    val allowNexusNotifications = stringResource(id = R.string.allow_nexus_notifications)
    val grantPermission = stringResource(id = R.string.grant_permission)
    val settings = stringResource(id = R.string.settings)
    val permissionDenied = stringResource(id = R.string.permission_denied)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current as Activity

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            scope.launch {
                val result = hostState.showSnackbar(
                    message =permissionDenied,
                    actionLabel = settings,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite)
                if (SnackbarResult.ActionPerformed == result) {
                    val intent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", context.packageName, null)
                        data = uri
                    }
                    context.startActivity(intent)
                }
            }

        }
    }


    LaunchedEffect(key1 = Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.POST_NOTIFICATIONS)) {
                val result = hostState.showSnackbar(
                    message = allowNexusNotifications,
                    actionLabel = grantPermission,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite)
                if (SnackbarResult.ActionPerformed == result) {
                    delay(3000)
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                delay(3000)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            delay(3000)
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}