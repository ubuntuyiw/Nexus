package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.button_style.SecondaryButton
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField
import com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state.ChatRoomUpdateState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray

@Composable
fun RenameChatRoomDialog(
    newName: String,
    newNameOnValueChange: (String) -> Unit,
    chatRoomUpdateState: ChatRoomUpdateState,
    approvalOnClick: () -> Unit,
    rejectOnClick: () -> Unit,
) {
    val cancel = stringResource(id = R.string.cancel)
    val newNameString = stringResource(id = R.string.new_name)
    val ok = stringResource(id = R.string.ok)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )

    ) {
        Column {

            PrimaryTextField(
                value = newName,
                onValueChange = newNameOnValueChange,
                singleLine = true,
                isError = newName.length > 25,
                trailingIcon = {
                    if (chatRoomUpdateState.isLoading)
                        PrimaryCircularProgressIndicator()
                },

                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = newNameString,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            text =  newName.length.toString(),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = if (newName.length > 25) MaterialTheme.colorScheme.error
                                    else Gray,
                            ),
                        )

                    }

                },
                modifier = Modifier.fillMaxWidth()
            )

            if (chatRoomUpdateState.isError)
                Text(
                    text = chatRoomUpdateState.errorMessage,
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
                SecondaryButton(
                    onClick = approvalOnClick,
                    enabled = newName.length <= 25
                ) {
                    Text(text = ok, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }



}