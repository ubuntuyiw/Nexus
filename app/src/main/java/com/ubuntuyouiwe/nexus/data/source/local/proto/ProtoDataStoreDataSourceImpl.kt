package com.ubuntuyouiwe.nexus.data.source.local.proto

import androidx.datastore.core.DataStore
import com.google.protobuf.BoolValue
import com.google.protobuf.FloatValue
import com.google.protobuf.Int32Value
import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProtoDataStoreDataSourceImpl @Inject constructor(
    private val settings: DataStore<SettingsDto>,
    private val chatRoomFilterDto: DataStore<ChatRoomFilterDto>,
    private val chatRoomShortDto: DataStore<ChatRoomShortDto>
) : ProtoDataStoreDataSource {
    init {
        runBlocking {
            val data = settings.data.first()
            if (!data.hasSetSpeechRate()) {
                updateSettings {
                    it.setSpeechRate = FloatValue.of(1f)
                }
            }

            if (!data.hasTheme()) {
                updateSettings {
                    it.theme = Int32Value.of(2)
                }
            }
        }

        runBlocking {
            val data = chatRoomShortDto.data.first()
            if (!data.hasIsNewestFirst()) {
                updateChatRoomShort {
                    it.isNewestFirst = BoolValue.of(true)
                }
            }
        }

        runBlocking {
            val data = chatRoomFilterDto.data.first()

            if (!data.hasIsAllRoles()) {
                updateChatRoomFilter {
                    it.isAllRoles = BoolValue.of(true)
                }
            }
            if (!data.hasIsNeutralMode()) {
                updateChatRoomFilter {
                    it.isNeutralMode = BoolValue.of(true)
                }
            }
            if (!data.hasIsDebateArena()) {
                updateChatRoomFilter {
                    it.isDebateArena = BoolValue.of(true)
                }
            }
            if (!data.hasIsTravelAdvisor()) {
                updateChatRoomFilter {
                    it.isTravelAdvisor = BoolValue.of(true)
                }
            }
            if (!data.hasIsAstrologer()) {
                updateChatRoomFilter {
                    it.isAstrologer = BoolValue.of(true)
                }
            }
            if (!data.hasIsChef()) {
                updateChatRoomFilter {
                    it.isChef = BoolValue.of(true)
                }
            }
            if (!data.hasIsLawyer()) {
                updateChatRoomFilter {
                    it.isLawyer = BoolValue.of(true)
                }
            }
            if (!data.hasIsDoctor()) {
                updateChatRoomFilter {
                    it.isDoctor = BoolValue.of(true)
                }
            }
            if (!data.hasIsIslamicScholar()) {
                updateChatRoomFilter {
                    it.isIslamicScholar = BoolValue.of(true)
                }
            }
            if (!data.hasIsBiologyTeacher()) {
                updateChatRoomFilter {
                    it.isBiologyTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsChemistryTeacher()) {
                updateChatRoomFilter {
                    it.isChemistryTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsGeographyTeacher()) {
                updateChatRoomFilter {
                    it.isGeographyTeacher = BoolValue.of(true)
                }
            }

            if (!data.hasIsHistoryTeacher()) {
                updateChatRoomFilter {
                    it.isHistoryTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsMathematicsTeacher()) {
                updateChatRoomFilter {
                    it.isMathematicsTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsPhysicsTeacher()) {
                updateChatRoomFilter {
                    it.isPhysicsTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsPsychologist()) {
                updateChatRoomFilter {
                    it.isPsychologist = BoolValue.of(true)
                }
            }
            if (!data.hasIsBishop()) {
                updateChatRoomFilter {
                    it.isBishop = BoolValue.of(true)
                }
            }
            if (!data.hasIsEnglishTeacher()) {
                updateChatRoomFilter {
                    it.isEnglishTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsRelationshipCoach()) {
                updateChatRoomFilter {
                    it.isRelationshipCoach = BoolValue.of(true)
                }
            }
            if (!data.hasIsVeterinarian()) {
                updateChatRoomFilter {
                    it.isVeterinarian = BoolValue.of(true)
                }
            }
            if (!data.hasIsSoftwareDeveloper()) {
                updateChatRoomFilter {
                    it.isSoftwareDeveloper = BoolValue.of(true)
                }
            }
            if (!data.hasIsSportsPolymath()) {
                updateChatRoomFilter {
                    it.isSportsPolymath = BoolValue.of(true)
                }
            }
            if (!data.hasIsLiteratureTeacher()) {
                updateChatRoomFilter {
                    it.isLiteratureTeacher = BoolValue.of(true)
                }
            }
            if (!data.hasIsPhilosophy()) {
                updateChatRoomFilter {
                    it.isPhilosophy = BoolValue.of(true)
                }
            }

            if (!data.hasIsFavorited()) {
                updateChatRoomFilter {
                    it.isFavorited = BoolValue.of(false)
                }
            }

            updateChatRoomFilter {
                it.isArchived = BoolValue.of(false)
            }
        }

    }


    override suspend fun updateSettings(updateAction: (SettingsDto.Builder) -> Unit): SettingsDto {
        return settings.updateData { currentSettings ->
            val builder = currentSettings.toBuilder()
            updateAction(builder)
            builder.build()
        }
    }

    override fun getSettings(): Flow<SettingsDto> = settings.data


    override suspend fun updateChatRoomFilter(updateAction: (ChatRoomFilterDto.Builder) -> Unit): ChatRoomFilterDto {
        return chatRoomFilterDto.updateData { currentSettings ->
            val builder = currentSettings.toBuilder()
            updateAction(builder)
            builder.build()
        }
    }

    override fun getChatRoomFilter(): Flow<ChatRoomFilterDto> = chatRoomFilterDto.data


    override suspend fun updateChatRoomShort(updateAction: (ChatRoomShortDto.Builder) -> Unit): ChatRoomShortDto {
        return chatRoomShortDto.updateData { currentSettings ->
            val builder = currentSettings.toBuilder()
            updateAction(builder)
            builder.build()
        }
    }

    override fun getChatRoomShort(): Flow<ChatRoomShortDto> = chatRoomShortDto.data


}