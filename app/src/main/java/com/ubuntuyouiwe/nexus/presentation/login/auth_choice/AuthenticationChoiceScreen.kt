package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.snacbar_style.PrimarySnackbar
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryButton
import com.ubuntuyouiwe.nexus.presentation.component.button_style.PrimaryVariantButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.SecondaryIcon
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.widgets.AppNameAnimation
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.widgets.LogoAnimation
import com.ubuntuyouiwe.nexus.presentation.login.widgets.GetAnnotatedTermsAndPrivacyText
import com.ubuntuyouiwe.nexus.presentation.state.ButtonState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun AuthenticationChoiceScreen(
    navController: NavController,
    googleSignInState: GoogleSignInState,
    googleSignInButtonState: ButtonState,
    googleSignInCheckAndStart: (ActivityResult) -> Unit,
    onEvent: (AuthChoiceEvent) -> Unit
) {
    val context = LocalContext.current
    val hostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = googleSignInState) {
        if (googleSignInState.isSuccess) {
            hostState.showSnackbar(context.resources.getString(R.string.loginSuccessful))

        } else if (googleSignInState.isLoading) {
            hostState.showSnackbar(
                message = context.resources.getString(R.string.loading),
                duration = SnackbarDuration.Indefinite
            )

        } else if (googleSignInState.isError) {
            hostState.showSnackbar(
                message = googleSignInState.errorMessage,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

        }
    }

    Scaffold(
        containerColor = White,
        snackbarHost = {
            SnackbarHost(hostState) { data ->
                PrimarySnackbar(
                    snackbarData = data,
                )
            }
        }
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(50f)
            ) {
                LogoAnimation()

                Spacer(modifier = Modifier.padding(16.dp))

                AppNameAnimation()
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(40f)
            ) {

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = googleSignInCheckAndStart
                )

                PrimaryVariantButton(
                    onClick = {
                        launcher.launch(googleSignInState.intent)
                    },
                    enabled = googleSignInButtonState.enabled,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(48.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.google),
                        contentDescription = stringResource(id = R.string.google_name)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = stringResource(id = R.string.google_name),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.padding(16.dp))

                PrimaryButton(
                    onClick = {
                        onEvent(AuthChoiceEvent.Email(navController))

                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(40.dp),


                    ) {
                    SecondaryIcon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = stringResource(id = R.string.email)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = stringResource(id = R.string.email),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.padding(16.dp))

                GetAnnotatedTermsAndPrivacyText(
                    termsOfUseOnClick = {
                        onEvent(AuthChoiceEvent.TermsOfUse {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        })
                    },
                    privacyPolicy = {
                        onEvent(AuthChoiceEvent.PrivacyPolicy {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        })
                    }
                )

                Spacer(modifier = Modifier.padding(16.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(10f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.From_UBUNTUYOUIWE),
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
            }


        }
    }


}

@Preview(showBackground = true, device = "id:pixel_7_pro")
@Composable
fun AuthenticationChoiceScreenPreview() {
    val navController = rememberNavController()

    NexusTheme {
        val googleSignInState = GoogleSignInState()
        val googleSignInButtonState = ButtonState()

        AuthenticationChoiceScreen(
            navController,
            googleSignInState,
            googleSignInButtonState,
            {}
        ) {}
    }
}
