package com.ubuntuyouiwe.nexus.presentation.widgets.terms_of_use

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.domain.model.TermsOfUseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsOfUseScreen(
    navController: NavHostController,
    getTermsOfUse: TermsOfUseModel
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = getTermsOfUse.title.EN,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = Icons.Default.Close.name,
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Acceptance",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = getTermsOfUse.acceptance.EN,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "License",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = getTermsOfUse.license.EN,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Limitations",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = getTermsOfUse.limitations.EN,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Disclaimer",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = getTermsOfUse.disclaimer.EN,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.padding(16.dp))
                val fullContactText = getTermsOfUse.contact.EN
                val emailPartIndex = fullContactText.indexOf("ubuntu@ubuntuyouiwe.com")
                val firstPart = fullContactText.substring(0, emailPartIndex)
                val emailPart = fullContactText.substring(emailPartIndex)

                val buyMessagesEN = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = MaterialTheme.typography.labelLarge.fontFamily,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            color = MaterialTheme.typography.labelLarge.color,
                        )

                    ) {
                        append(firstPart)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.inversePrimary,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    ) {
                        pushStringAnnotation(
                            tag = "go",
                            annotation = "go"
                        )
                        append(emailPart)
                        pop()
                    }

                }
                Text(
                    text = "Contact",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                val context = LocalContext.current
                ClickableText(
                    text =  buyMessagesEN,
                    softWrap = true,
                    onClick = { offset ->
                        buyMessagesEN.getStringAnnotations(
                            tag = "go",
                            start = offset, end = offset
                        ).firstOrNull()?.let { annotation ->
                            when (annotation.item) {
                                "go" -> {
                                    val email = "ubuntu@ubuntuyouiwe.com"
                                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("mailto:$email")
                                    }
                                    context.startActivity(Intent.createChooser(emailIntent, "E-posta Gönder"))
                                }
                            }
                        }
                    },
                    style = MaterialTheme.typography.labelLarge.copy(
                        textAlign = TextAlign.Start
                    ),
                )
            }


            
            
        }
    }
}