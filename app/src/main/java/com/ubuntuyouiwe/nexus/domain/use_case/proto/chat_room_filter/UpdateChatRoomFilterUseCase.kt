package com.ubuntuyouiwe.nexus.domain.use_case.proto.chat_room_filter

import com.google.protobuf.BoolValue
import com.google.protobuf.FloatValue
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomFilterRepository
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateChatRoomFilterUseCase @Inject constructor(
    private val chatRoomFilterRepository: ChatRoomFilterRepository
) {
    operator fun invoke(chatRoomFilter: ChatRoomFilter): Flow<Resource<ChatRoomFilter>> = flow {
        emit(Resource.Loading)

        try {
            val updatedData = chatRoomFilterRepository.updateChatRoomFilter {
                it.isAllRoles = BoolValue.of(chatRoomFilter.isAllRoles)
                it.isNeutralMode = BoolValue.of(chatRoomFilter.isNeutralMode)
                it.isDebateArena = BoolValue.of(chatRoomFilter.isDebateArena)
                it.isTravelAdvisor = BoolValue.of(chatRoomFilter.isTravelAdvisor)
                it.isAstrologer = BoolValue.of(chatRoomFilter.isAstrologer)
                it.isChef = BoolValue.of(chatRoomFilter.isChef)
                it.isLawyer = BoolValue.of(chatRoomFilter.isLawyer)
                it.isDoctor = BoolValue.of(chatRoomFilter.isDoctor)
                it.isIslamicScholar = BoolValue.of(chatRoomFilter.isIslamicScholar)
                it.isBiologyTeacher = BoolValue.of(chatRoomFilter.isBiologyTeacher)
                it.isChemistryTeacher = BoolValue.of(chatRoomFilter.isChemistryTeacher)
                it.isGeographyTeacher = BoolValue.of(chatRoomFilter.isGeographyTeacher)
                it.isHistoryTeacher = BoolValue.of(chatRoomFilter.isHistoryTeacher)
                it.isMathematicsTeacher = BoolValue.of(chatRoomFilter.isMathematicsTeacher)
                it.isPhysicsTeacher = BoolValue.of(chatRoomFilter.isPhysicsTeacher)
                it.isPsychologist = BoolValue.of(chatRoomFilter.isPsychologist)
                it.isBishop = BoolValue.of(chatRoomFilter.isBishop)
                it.isEnglishTeacher = BoolValue.of(chatRoomFilter.isEnglishTeacher)
                it.isRelationshipCoach = BoolValue.of(chatRoomFilter.isRelationshipCoach)
                it.isVeterinarian = BoolValue.of(chatRoomFilter.isVeterinarian)
                it.isSoftwareDeveloper = BoolValue.of(chatRoomFilter.isSoftwareDeveloper)
                it.isFavorited = BoolValue.of(chatRoomFilter.isFavorited)
                it.isArchived = BoolValue.of(chatRoomFilter.isArchived)
            }

            emit(Resource.Success(updatedData))


        } catch (e: Exception) {
            emit(Resource.Error(message = e.message?: ErrorCodes.UNKNOWN_ERROR.name))

        }



    }
}