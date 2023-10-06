package com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ubuntuyouiwe.nexus.presentation.component.pogress_style.PrimaryCircularProgressIndicator
import com.ubuntuyouiwe.nexus.presentation.main_activity.UserOperationState
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White
import com.ubuntuyouiwe.nexus.presentation.util.RolesCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PurposeSelectionScreen(
    navController: NavHostController,
    isAuto: Boolean,
    purposeSelectionState: UserOperationState,
    updatePurposeSelection: UpdatePurposeSelectionState,
    onEvent: (event: PurposeSelectionEvent) -> Unit
) {

    var widthPx by remember { mutableIntStateOf(0) }
    var withDp by remember { mutableStateOf(0.dp) }
    withDp = with(LocalDensity.current) { widthPx.toDp() }

    val getPurposeSelection = purposeSelectionState.successData?.purposeSelection
    val purposeSelection = listOf(
        mapOf(RolesCategory.DebateArena to getPurposeSelection?.isDebateArena),
        mapOf(RolesCategory.TravelAdvisor to getPurposeSelection?.isTravelAdvisor),
        mapOf(RolesCategory.Astrologer to getPurposeSelection?.isAstrologer),
        mapOf(RolesCategory.Chef to purposeSelectionState.successData?.purposeSelection?.isChef),
        mapOf(RolesCategory.SportsPolymath to purposeSelectionState.successData?.purposeSelection?.isSportsPolymath),
        mapOf(RolesCategory.LiteratureTeacher to purposeSelectionState.successData?.purposeSelection?.isLiteratureTeacher),
        mapOf(RolesCategory.Philosophy to purposeSelectionState.successData?.purposeSelection?.isPhilosophy),
        mapOf(RolesCategory.Lawyer to purposeSelectionState.successData?.purposeSelection?.isLawyer),
        mapOf(RolesCategory.Doctor to purposeSelectionState.successData?.purposeSelection?.isDoctor),
        mapOf(RolesCategory.IslamicScholar to purposeSelectionState.successData?.purposeSelection?.isIslamicScholar),
        mapOf(RolesCategory.BiologyTeacher to purposeSelectionState.successData?.purposeSelection?.isBiologyTeacher),
        mapOf(RolesCategory.ChemistryTeacher to purposeSelectionState.successData?.purposeSelection?.isChemistryTeacher),
        mapOf(RolesCategory.EnglishTeacher to purposeSelectionState.successData?.purposeSelection?.isEnglishTeacher),
        mapOf(RolesCategory.GeographyTeacher to purposeSelectionState.successData?.purposeSelection?.isGeographyTeacher),
        mapOf(RolesCategory.HistoryTeacher to purposeSelectionState.successData?.purposeSelection?.isHistoryTeacher),
        mapOf(RolesCategory.MathematicsTeacher to purposeSelectionState.successData?.purposeSelection?.isMathematicsTeacher),
        mapOf(RolesCategory.PhysicsTeacher to purposeSelectionState.successData?.purposeSelection?.isPhysicsTeacher),
        mapOf(RolesCategory.Psychologist to purposeSelectionState.successData?.purposeSelection?.isPsychologist),
        mapOf(RolesCategory.Bishop to purposeSelectionState.successData?.purposeSelection?.isBishop),
        mapOf(RolesCategory.RelationshipCoach to purposeSelectionState.successData?.purposeSelection?.isRelationshipCoach),
        mapOf(RolesCategory.Veterinarian to purposeSelectionState.successData?.purposeSelection?.isVeterinarian),
        mapOf(RolesCategory.SoftwareDeveloper to purposeSelectionState.successData?.purposeSelection?.isSoftwareDeveloper),
    )
    val minSelected = purposeSelection.filter { rolesCategoryBooleanMap ->
        rolesCategoryBooleanMap.values.first() ?: false
    }.size
    val hostState = remember {
        SnackbarHostState()

    }
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = {
            SnackbarHost(hostState) {
                Snackbar(snackbarData = it)
            }
        },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        }
                    ) {
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = Icons.Default.ArrowBack.name,
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                    }
                },
                title = {
                    Text(
                        text = "App Use Intent",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {

                    if (updatePurposeSelection.isLoading) {
                        PrimaryCircularProgressIndicator()
                    }

                }
            )
        },
        bottomBar = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (minSelected < 3) {
                            scope.launch {
                                hostState.showSnackbar("A minimum of 3 selections is required.")
                            }
                        } else if(!updatePurposeSelection.isLoading && !updatePurposeSelection.isError) {
                            if (isAuto) {
                                navController.navigate(Screen.SystemMessage.name + "/${true}")
                            } else {
                                navController.navigateUp()
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = if (isAuto) "Next" else "Save",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "$minSelected",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "By selecting these options, Nexus can get to know you better. This will allow for more personalized topic suggestions and notifications.",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if (updatePurposeSelection.isError) {
                    Text(
                        text = updatePurposeSelection.errorMessage,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    FlowRow(
                        maxItemsInEachRow = 5,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        purposeSelection.forEach { selection ->

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = selection.values.first() ?: false,
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    onCheckedChange = {
                                        getPurposeSelection?.let { purposeSelection ->
                                            onEvent(
                                                PurposeSelectionEvent.UpdatePurposeSelection(
                                                    when (selection.keys.first()) {
                                                        RolesCategory.DebateArena -> {
                                                            purposeSelection.copy(isDebateArena = it)
                                                        }

                                                        RolesCategory.Doctor -> {
                                                            purposeSelection.copy(isDoctor = it)
                                                        }

                                                        RolesCategory.Chef -> {
                                                            purposeSelection.copy(isChef = it)
                                                        }

                                                        RolesCategory.LiteratureTeacher -> {
                                                            purposeSelection.copy(
                                                                isLiteratureTeacher = it
                                                            )
                                                        }

                                                        RolesCategory.Philosophy -> {
                                                            purposeSelection.copy(isPhilosophy = it)
                                                        }

                                                        RolesCategory.SportsPolymath -> {
                                                            purposeSelection.copy(isSportsPolymath = it)
                                                        }

                                                        RolesCategory.Lawyer -> {
                                                            purposeSelection.copy(isLawyer = it)
                                                        }

                                                        RolesCategory.RelationshipCoach -> {
                                                            purposeSelection.copy(
                                                                isRelationshipCoach = it
                                                            )
                                                        }

                                                        RolesCategory.IslamicScholar -> {
                                                            purposeSelection.copy(isIslamicScholar = it)
                                                        }

                                                        RolesCategory.Astrologer -> {
                                                            purposeSelection.copy(isAstrologer = it)
                                                        }

                                                        RolesCategory.Bishop -> {
                                                            purposeSelection.copy(isBishop = it)
                                                        }

                                                        RolesCategory.Psychologist -> {
                                                            purposeSelection.copy(isPsychologist = it)
                                                        }

                                                        RolesCategory.PhysicsTeacher -> {
                                                            purposeSelection.copy(isPhysicsTeacher = it)
                                                        }

                                                        RolesCategory.ChemistryTeacher -> {
                                                            purposeSelection.copy(isChemistryTeacher = it)
                                                        }

                                                        RolesCategory.BiologyTeacher -> {
                                                            purposeSelection.copy(isBiologyTeacher = it)
                                                        }

                                                        RolesCategory.MathematicsTeacher -> {
                                                            purposeSelection.copy(
                                                                isMathematicsTeacher = it
                                                            )
                                                        }

                                                        RolesCategory.GeographyTeacher -> {
                                                            purposeSelection.copy(isGeographyTeacher = it)
                                                        }

                                                        RolesCategory.EnglishTeacher -> {
                                                            purposeSelection.copy(isEnglishTeacher = it)
                                                        }

                                                        RolesCategory.HistoryTeacher -> {
                                                            purposeSelection.copy(isHistoryTeacher = it)
                                                        }

                                                        RolesCategory.Veterinarian -> {
                                                            purposeSelection.copy(isVeterinarian = it)
                                                        }

                                                        RolesCategory.SoftwareDeveloper -> {
                                                            purposeSelection.copy(
                                                                isSoftwareDeveloper = it
                                                            )
                                                        }

                                                        RolesCategory.TravelAdvisor -> {
                                                            purposeSelection.copy(isTravelAdvisor = it)
                                                        }

                                                        else -> {
                                                            purposeSelection.copy(isDebateArena = it)
                                                        }

                                                    }
                                                )
                                            )

                                        }

                                    }
                                )

                                Text(
                                    text = selection.keys.first().roleName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    onTextLayout = {
                                        widthPx =
                                            if (widthPx < it.size.width) it.size.width else widthPx
                                    },
                                    modifier = if (widthPx > 0) Modifier
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(4.dp)
                                        .width(withDp)
                                    else Modifier.padding(4.dp),

                                    )
                            }

                        }


                    }

                }


            }
        }

    }
}