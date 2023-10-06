package com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.icon_style.PrimaryIcon
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryHintText
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state.ResetPasswordState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DarkGray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.LightGray
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White
import com.ubuntuyouiwe.nexus.presentation.ui.theme.VeryLightGray

@Composable
fun ForgotPasswordDialog(
    emailValue: String,
    onValueChangeEmailValue: (String) -> Unit,
    resetPasswordState: ResetPasswordState,
    approvalOnClick: () -> Unit,
    rejectOnClick: () -> Unit,
) {
    val passwordVisibility = stringResource(id = R.string.password_visibility)
    val resetPassword = stringResource(id = R.string.reset_password)
    val email = stringResource(id = R.string.email)
    val enterEmailForReset = stringResource(id = R.string.enter_email_for_reset)
    val cancel = stringResource(id = R.string.cancel)
    val passwordResetEmail = stringResource(id = R.string.password_reset_email)
    val send = stringResource(id = R.string.send)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .heightIn(min = 260.dp)
                .fillMaxWidth()


        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.reset_password
                        ),
                        contentDescription = passwordVisibility,
                    )
                    Text(
                        text = resetPassword,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                if (resetPasswordState.isLoading) {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(16.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = LightGray
                        )

                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp),
                            strokeCap = StrokeCap.Square,
                            strokeWidth = 3.dp)
                    }
                }

            }

            PrimaryTextField(
                value = emailValue,
                onValueChange = onValueChangeEmailValue,
                singleLine = true,
                label = {
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                placeholder = {
                    Text(
                        text = enterEmailForReset,
                        style = MaterialTheme.typography.bodySmall,
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (resetPasswordState.isSuccess)
                Text(
                    text = passwordResetEmail,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            if (resetPasswordState.isErrorState)
                Text(
                    text = resetPasswordState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(75.dp)
                    .background(MaterialTheme.colorScheme.scrim)
            ) {
                SecondaryButton(onClick = rejectOnClick) {
                    Text(text = cancel, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.padding(16.dp))
                SecondaryButton(onClick = approvalOnClick) {
                    Text(text = send, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }

        }
    }
}