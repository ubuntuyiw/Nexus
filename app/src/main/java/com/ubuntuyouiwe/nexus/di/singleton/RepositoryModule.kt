package com.ubuntuyouiwe.nexus.di.singleton

import android.content.Context
import com.ubuntuyouiwe.nexus.data.repository.AuthRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.ChatRoomFilterRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.ChatRoomShortRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.DataSyncRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.MlKitRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.SettingsProtoRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.RoleRepositoryImpl
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSource
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSource
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.MlKit
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomFilterRepository
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomShortRepository
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.domain.repository.MlKitRepository
import com.ubuntuyouiwe.nexus.domain.repository.SettingsProtoRepository
import com.ubuntuyouiwe.nexus.domain.repository.RoleRepository
import com.ubuntuyouiwe.nexus.domain.util.Preconditions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseDataSource: FirebaseDataSource,
    ): AuthRepository =
        AuthRepositoryImpl(firebaseDataSource)

    @Provides
    @Singleton
    fun providePreconditions() = Preconditions()

    @Provides
    @Singleton
    fun provideDataSyncRepository(
        firebaseDataSource: FirebaseDataSource,
        @ApplicationContext context: Context,
    ): DataSyncRepository =
        DataSyncRepositoryImpl(firebaseDataSource, context)


    @Provides
    @Singleton
    fun provideRoleRepository(
        roleDataSource: RoleDataSource
    ): RoleRepository {
        return RoleRepositoryImpl(roleDataSource)
    }

    @Provides
    @Singleton
    fun provideMlKiteRepository(
        mlKit: MlKit
    ): MlKitRepository {
        return MlKitRepositoryImpl(mlKit)
    }

    @Provides
    @Singleton
    fun provideSettingsProtoRepository(
        protoDataStoreDataSource: ProtoDataStoreDataSource
    ): SettingsProtoRepository {
        return SettingsProtoRepositoryImpl(protoDataStoreDataSource)
    }
    @Provides
    @Singleton
    fun provideChatRoomFilterProtoRepository(
        protoDataStoreDataSource: ProtoDataStoreDataSource
    ): ChatRoomFilterRepository {
        return ChatRoomFilterRepositoryImpl(protoDataStoreDataSource)
    }
    @Provides
    @Singleton
    fun provideChatRoomShortProtoRepository(
        protoDataStoreDataSource: ProtoDataStoreDataSource
    ): ChatRoomShortRepository {
        return ChatRoomShortRepositoryImpl(protoDataStoreDataSource)
    }





}