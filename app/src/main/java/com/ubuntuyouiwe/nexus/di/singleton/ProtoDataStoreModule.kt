package com.ubuntuyouiwe.nexus.di.singleton

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.data.source.local.proto.util.createProtoDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProtoDataStoreModule {

    @Provides
    @Singleton
    fun provideSettingsSerializer(): Serializer<SettingsDto> = object: Serializer<SettingsDto> {
        override val defaultValue: SettingsDto = SettingsDto.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): SettingsDto {
            try {
                return SettingsDto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }
        override suspend fun writeTo(
            t: SettingsDto,
            output: OutputStream
        ) = t.writeTo(output)
    }
    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context,
        settingsSerializer: Serializer<SettingsDto>
    ): DataStore<SettingsDto> = context.createProtoDataStore(
        fileName = "settings.pb",
        serializer = settingsSerializer
    )

    @Provides
    @Singleton
    fun provideChatRoomFilterSerializer(): Serializer<ChatRoomFilterDto> = object: Serializer<ChatRoomFilterDto> {
        override val defaultValue: ChatRoomFilterDto = ChatRoomFilterDto.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): ChatRoomFilterDto {
            try {
                return ChatRoomFilterDto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }
        override suspend fun writeTo(
            t: ChatRoomFilterDto,
            output: OutputStream
        ) = t.writeTo(output)
    }
    @Provides
    @Singleton
    fun provideChatRoomFilterDataStore(
        @ApplicationContext context: Context,
        settingsSerializer: Serializer<ChatRoomFilterDto>
    ): DataStore<ChatRoomFilterDto> = context.createProtoDataStore(
        fileName = "chat_room_filter.pb",
        serializer = settingsSerializer
    )


    @Provides
    @Singleton
    fun provideChatRoomShortSerializer(): Serializer<ChatRoomShortDto> = object: Serializer<ChatRoomShortDto> {
        override val defaultValue: ChatRoomShortDto = ChatRoomShortDto.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): ChatRoomShortDto {
            try {
                return ChatRoomShortDto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }
        override suspend fun writeTo(
            t: ChatRoomShortDto,
            output: OutputStream
        ) = t.writeTo(output)
    }
    @Provides
    @Singleton
    fun provideChatRoomShortDataStore(
        @ApplicationContext context: Context,
        settingsSerializer: Serializer<ChatRoomShortDto>
    ): DataStore<ChatRoomShortDto> = context.createProtoDataStore(
        fileName = "chat_room_short.pb",
        serializer = settingsSerializer
    )




}