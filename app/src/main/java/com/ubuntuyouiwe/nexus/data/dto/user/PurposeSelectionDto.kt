package com.ubuntuyouiwe.nexus.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class PurposeSelectionDto(
    @field:JvmField
    val isDebateArena: Boolean = false,
    @field:JvmField
    val isTravelAdvisor: Boolean = false,
    @field:JvmField
    val isAstrologer: Boolean = false,
    @field:JvmField
    val isChef: Boolean = false,
    @field:JvmField
    val isSportsPolymath: Boolean = false,
    @field:JvmField
    val isLiteratureTeacher: Boolean = false,
    @field:JvmField
    val isPhilosophy: Boolean = false,
    @field:JvmField
    val isLawyer: Boolean = false,
    @field:JvmField
    val isDoctor: Boolean = false,
    @field:JvmField
    val isIslamicScholar: Boolean = false,
    @field:JvmField
    val isBiologyTeacher: Boolean = false,
    @field:JvmField
    val isChemistryTeacher: Boolean = false,
    @field:JvmField
    val isGeographyTeacher: Boolean = false,
    @field:JvmField
    val isHistoryTeacher: Boolean = false,
    @field:JvmField
    val isMathematicsTeacher: Boolean = false,
    @field:JvmField
    val isPhysicsTeacher: Boolean = false,
    @field:JvmField
    val isPsychologist: Boolean = false,
    @field:JvmField
    val isBishop: Boolean = false,
    @field:JvmField
    val isEnglishTeacher: Boolean = false,
    @field:JvmField
    val isRelationshipCoach: Boolean = false,
    @field:JvmField
    val isVeterinarian: Boolean = false,
    @field:JvmField
    val isSoftwareDeveloper: Boolean = false,
)
