package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.widgets.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomFilterState
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state.ChatRoomShortState
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.util.RolesCategory
import com.ubuntuyouiwe.nexus.presentation.util.ShortDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterDialog(
    chatRoomShortState: ChatRoomShortState,
    chatRoomShortOnEvent: (ShortDate) -> Unit,
    chatRoomFilterState: ChatRoomFilterState,
    setAllSelectedRole: ( allSelectedRole: Boolean) -> Unit,
    chatRoomFilterOnEvent: (Boolean, RolesCategory) -> Unit
) {
    val isFavorited =  mapOf(RolesCategory.JustFavorited to chatRoomFilterState.data.isFavorited)
    var allSelectedRole by remember {
        mutableStateOf(false)
    }

    val roles = listOf(
        mapOf(RolesCategory.NeutralMode to chatRoomFilterState.data.isNeutralMode),
        mapOf(RolesCategory.DebateArena to chatRoomFilterState.data.isDebateArena),
        mapOf(RolesCategory.TravelAdvisor to chatRoomFilterState.data.isTravelAdvisor),
        mapOf(RolesCategory.Astrologer to chatRoomFilterState.data.isAstrologer),
        mapOf(RolesCategory.Chef to chatRoomFilterState.data.isChef),
        mapOf(RolesCategory.SportsPolymath to chatRoomFilterState.data.isSportsPolymath),
        mapOf(RolesCategory.LiteratureTeacher to chatRoomFilterState.data.isLiteratureTeacher),
        mapOf(RolesCategory.Philosophy to chatRoomFilterState.data.isPhilosophy),
        mapOf(RolesCategory.Lawyer to chatRoomFilterState.data.isLawyer),
        mapOf(RolesCategory.Doctor to chatRoomFilterState.data.isDoctor),
        mapOf(RolesCategory.IslamicScholar to chatRoomFilterState.data.isIslamicScholar),
        mapOf(RolesCategory.BiologyTeacher to chatRoomFilterState.data.isBiologyTeacher),
        mapOf(RolesCategory.ChemistryTeacher to chatRoomFilterState.data.isChemistryTeacher),
        mapOf(RolesCategory.EnglishTeacher to chatRoomFilterState.data.isEnglishTeacher),
        mapOf(RolesCategory.GeographyTeacher to chatRoomFilterState.data.isGeographyTeacher),
        mapOf(RolesCategory.HistoryTeacher to chatRoomFilterState.data.isHistoryTeacher),
        mapOf(RolesCategory.MathematicsTeacher to chatRoomFilterState.data.isMathematicsTeacher),
        mapOf(RolesCategory.PhysicsTeacher to chatRoomFilterState.data.isPhysicsTeacher),
        mapOf(RolesCategory.Psychologist to chatRoomFilterState.data.isPsychologist),
        mapOf(RolesCategory.Bishop to chatRoomFilterState.data.isBishop),
        mapOf(RolesCategory.RelationshipCoach to chatRoomFilterState.data.isRelationshipCoach),
        mapOf(RolesCategory.Veterinarian to chatRoomFilterState.data.isVeterinarian),
        mapOf(RolesCategory.SoftwareDeveloper to chatRoomFilterState.data.isSoftwareDeveloper),
    )
    for (role in roles) {
        val isSelected = role.values.first()
        allSelectedRole = true
        if (!isSelected) {
            allSelectedRole = false
            break
        }
    }
    LaunchedEffect(key1 = chatRoomFilterState.data.isAllRoles != allSelectedRole ) {
        if (chatRoomFilterState.data.isAllRoles != allSelectedRole) {
            setAllSelectedRole(allSelectedRole)
        }
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Filter", style = MaterialTheme.typography.titleMedium)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Checkbox(
                checked = isFavorited.values.first(),
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.scrim,
                    uncheckedColor = MaterialTheme.colorScheme.onBackground
                ),
                onCheckedChange = {
                chatRoomFilterOnEvent(it, isFavorited.keys.first())
            })
            Text(text = isFavorited.keys.first().roleName, style = MaterialTheme.typography.bodyMedium)

        }


    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var selectedShortDate by remember {
            mutableStateOf(
                when (chatRoomShortState.data.isNewestFirst) {
                    true -> ShortDate.NewestFirst
                    false -> ShortDate.LatestFirst
                }
            )
        }

        val short = listOf<ShortDate>(ShortDate.NewestFirst, ShortDate.LatestFirst)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.scrim
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Chapter(
                top = {
                    Text(
                        text = "Short",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                bottom = {
                    FlowRow(
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Center,
                        maxItemsInEachRow = 3
                    ) {
                        short.forEach { shortDate ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                            ) {
                                RadioButton(
                                    selected = shortDate == selectedShortDate,
                                    onClick = {
                                        selectedShortDate = shortDate
                                        chatRoomShortOnEvent(shortDate)
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.onSurface,
                                        unselectedColor = MaterialTheme.colorScheme.onSurface,
                                    )
                                )
                                Text(
                                    text = shortDate.shortName,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                            }


                        }
                    }

                }
            )
        }



        var widthPx by remember { mutableIntStateOf(0) }
        var withDp by remember { mutableStateOf(0.dp) }
        withDp = with(LocalDensity.current) { widthPx.toDp() }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.scrim
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Chapter(
                top = {
                    Text(
                        text = "Roles Filter",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                bottom = {
                    FlowRow(
                        maxItemsInEachRow = 5,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = allSelectedRole,
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = MaterialTheme.colorScheme.onSurface
                                ),
                                onCheckedChange = {
                                    chatRoomFilterOnEvent(it, RolesCategory.All)
                                }
                            )

                            Text(
                                text = "All",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = if (widthPx > 0) Modifier
                                    .padding(4.dp)
                                    .width(withDp)
                                else Modifier.padding(4.dp),
                            )
                        }
                        roles.forEach { map ->
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = map.values.first(),
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    onCheckedChange = {
                                        chatRoomFilterOnEvent(it, map.keys.first())
                                    }
                                )

                                Text(
                                    text = map.keys.first().roleName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    onTextLayout = {
                                        widthPx = if (widthPx < it.size.width) it.size.width else widthPx
                                    },
                                    modifier = if (widthPx > 0) Modifier
                                        .padding(4.dp)
                                        .width(withDp)
                                    else Modifier.padding(4.dp),

                                    )
                            }

                        }

                    }
                }
            )
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true)
@Composable
fun EmailWithSignUpPreview() {
    NexusTheme(true) {

        //FilterDialog(chatRoomShortState, chatRoomFilterState)
    }
}