package com.ubuntuyouiwe.nexus.domain.use_case.firestore

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.presentation.util.RolesCategory
import com.ubuntuyouiwe.nexus.util.Resource
import com.ubuntuyouiwe.nexus.util.erros.ErrorCodes
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatRoomsUseCase @Inject constructor(
    private val dataSyncRepository: DataSyncRepository
) {

    operator fun invoke(short: Boolean, filter: ChatRoomFilter) = flow<Resource<ChatRooms>> {
        emit(Resource.Loading)
        dataSyncRepository.getChatRooms().catch {
            emit(Resource.Error(message = it.message ?: ErrorCodes.UNKNOWN_ERROR.name))
        }.map { chatRooms ->
            val filterFavorited = filterFavorited(chatRooms, filter)
            val filterRoles = filterRoles(filterFavorited, filter)
            val filterArchived = filterArchived(filterRoles, filter)
            val shortDate = short(filterArchived, short)

            sortMessages(shortDate)


        }.collect {
            emit(Resource.Success(it))

        }


    }

    private fun sortMessages(chatRooms: ChatRooms): ChatRooms {
        val pinnedMessages = chatRooms.messageResult.filter { it.isPinned }
        val unpinnedMessages = chatRooms.messageResult.filter { !it.isPinned }

        return chatRooms.copy(
            messageResult = pinnedMessages + unpinnedMessages
        )
    }

    private fun short(chatRooms: ChatRooms, short: Boolean): ChatRooms {
        return if (!short) {
            chatRooms.copy(
                messageResult = chatRooms.messageResult.reversed()
            )
        } else chatRooms
    }

    private fun filterFavorited(chatRooms: ChatRooms, filter: ChatRoomFilter): ChatRooms {
        return chatRooms.copy(
            messageResult = chatRooms.messageResult.filter {
                if (filter.isFavorited) it.isFavorited else true
            }
        )
    }

    private fun filterArchived(chatRooms: ChatRooms, filter: ChatRoomFilter): ChatRooms {
        return chatRooms.copy(
            messageResult = chatRooms.messageResult.filter {
                if (filter.isArchived) it.isArchived else !it.isArchived
            }
        )
    }


    private fun filterRoles(chatRooms: ChatRooms, filter: ChatRoomFilter): ChatRooms {
        return chatRooms.copy(
            messageResult = chatRooms.messageResult.filter { message ->
                listOf(
                    filter.isNeutralMode && message.roleId == RolesCategory.NeutralMode.id,
                    filter.isDebateArena && message.roleId == RolesCategory.DebateArena.id,
                    filter.isTravelAdvisor && message.roleId == RolesCategory.TravelAdvisor.id,
                    filter.isAstrologer && message.roleId == RolesCategory.Astrologer.id,
                    filter.isChef && message.roleId == RolesCategory.Chef.id,
                    filter.isSportsPolymath && message.roleId == RolesCategory.SportsPolymath.id,
                    filter.isLiteratureTeacher && message.roleId == RolesCategory.LiteratureTeacher.id,
                    filter.isPhilosophy && message.roleId == RolesCategory.Philosophy.id,
                    filter.isLawyer && message.roleId == RolesCategory.Lawyer.id,
                    filter.isDoctor && message.roleId == RolesCategory.Doctor.id,
                    filter.isIslamicScholar && message.roleId == RolesCategory.IslamicScholar.id,
                    filter.isBiologyTeacher && message.roleId == RolesCategory.BiologyTeacher.id,
                    filter.isChemistryTeacher && message.roleId == RolesCategory.ChemistryTeacher.id,
                    filter.isGeographyTeacher && message.roleId == RolesCategory.GeographyTeacher.id,
                    filter.isHistoryTeacher && message.roleId == RolesCategory.HistoryTeacher.id,
                    filter.isMathematicsTeacher && message.roleId == RolesCategory.MathematicsTeacher.id,
                    filter.isPhysicsTeacher && message.roleId == RolesCategory.PhysicsTeacher.id,
                    filter.isPsychologist && message.roleId == RolesCategory.Psychologist.id,
                    filter.isBishop && message.roleId == RolesCategory.Bishop.id,
                    filter.isEnglishTeacher && message.roleId == RolesCategory.EnglishTeacher.id,
                    filter.isRelationshipCoach && message.roleId == RolesCategory.RelationshipCoach.id,
                    filter.isVeterinarian && message.roleId == RolesCategory.Veterinarian.id,
                    filter.isSoftwareDeveloper && message.roleId == RolesCategory.SoftwareDeveloper.id,
                ).any { it }
            }
        )
    }
}