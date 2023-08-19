package com.ubuntuyouiwe.nexus.presentation.login.email_with_login.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
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
                    PrimaryIcon(
                        painter = painterResource(
                            id = R.drawable.reset_password
                        ),
                        contentDescription = stringResource(id = R.string.passwordVisibility),
                    )
                    Text(
                        text = stringResource(id = R.string.resetPassword),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                if (resetPasswordState.isLoading) {
                    PrimaryCircularProgressIndicator()

                }

            }

            PrimaryTextField(
                value = emailValue,
                onValueChange = onValueChangeEmailValue,
                singleLine = true,
                label = { PrimaryHintText(stringResource(id = R.string.email)) },
                placeholder = { PrimaryHintText(stringResource(id = R.string.enterTheEmailForPasswordReset)) },
                modifier = Modifier.fillMaxWidth(0.8f)
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
                    .background(VeryLightGray)
            ) {
                SecondaryButton(onClick = rejectOnClick) {
                    Text(text = "Cancel", modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.padding(16.dp))
                SecondaryButton(onClick = approvalOnClick) {
                    Text(text = "Send", modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }

        }
    }
}