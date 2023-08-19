package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatRoom(
    chatRoom: ChatRoom,
    onClick: () -> Unit

) {

    Box(modifier = Modifier
        .clickable { onClick() }
        .animateContentSize()) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            GlideImage(
                model = chatRoom.role.image,
                contentDescription = "",
                modifier = Modifier.size(40.dp)

            )

            Spacer(modifier = Modifier.padding(start = 4.dp))


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = chatRoom.name,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = chatRoom.role.name.EN,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,

                        ) {
                        if (chatRoom.isFavorited) {


                        }

                        /*Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.pin_svg),
                            contentDescription = "",
                            tint = Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.star),
                            contentDescription = "",
                            tint = Gray,
                            modifier = Modifier.size(14.dp)
                        )*/
                    }
                }

                Spacer(modifier = Modifier.padding(6.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "son mesaj",
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        maxLines = 1,
                        color = Gray,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)

                    )

                    Text(
                        text = "21.10.2023 ",
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        maxLines = 1,
                        color = Gray,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }


        }
    }


}