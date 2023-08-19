package com.ubuntuyouiwe.nexus.di

import android.content.Context
import com.ubuntuyouiwe.nexus.data.repository.AuthRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.DataSyncRepositoryImpl
import com.ubuntuyouiwe.nexus.data.repository.RoleRepositoryImpl
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSource
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.domain.repository.RoleRepository
import com.ubuntuyouiwe.nexus.domain.util.Preconditions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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




}