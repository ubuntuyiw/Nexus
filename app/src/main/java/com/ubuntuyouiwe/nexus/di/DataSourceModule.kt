package com.ubuntuyouiwe.nexus.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSource
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSourceImpl
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFireStoreDataSource(
        fireStore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        firebaseMessaging: FirebaseMessaging,
        functions: FirebaseFunctions,
        googleClientId: GoogleSignInClient,
        json: Json
    ): FirebaseDataSource {

        return FirebaseDataSourceImpl(fireStore, firebaseAuth, firebaseMessaging, functions, googleClientId, json)
    }

    @Provides
    @Singleton
    fun provideRoleDataSource(
        roles: List<RoleDto>
    ): RoleDataSource {
        return RoleDataSourceImpl(roles)
    }

}