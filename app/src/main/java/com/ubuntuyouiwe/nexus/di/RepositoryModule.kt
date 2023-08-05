package com.ubuntuyouiwe.nexus.di

import com.ubuntuyouiwe.nexus.data.repository.AuthRepositoryImpl
import com.ubuntuyouiwe.nexus.data.source.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.util.Preconditions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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


}