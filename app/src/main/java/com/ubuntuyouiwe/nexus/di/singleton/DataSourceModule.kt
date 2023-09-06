package com.ubuntuyouiwe.nexus.di.singleton

import androidx.datastore.core.DataStore
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.mlkit.vision.text.TextRecognizer
import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSource
import com.ubuntuyouiwe.nexus.data.source.local.RoleDataSourceImpl
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSource
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSourceImpl
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSourceImpl
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.MlKit
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.MlKitImpl
import com.ubuntuyouiwe.nexus.di.RecognizerDefault

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    ): FirebaseDataSource {

        return FirebaseDataSourceImpl(fireStore, firebaseAuth, firebaseMessaging, functions, googleClientId)
    }

    @Provides
    @Singleton
    fun provideRoleDataSource(
        roles: List<RoleDto>
    ): RoleDataSource {
        return RoleDataSourceImpl(roles)
    }

    @Provides
    @Singleton
    fun provideMlKitDataSource(
        @RecognizerDefault
        recognizer: TextRecognizer
    ): MlKit {
        return MlKitImpl(recognizer)
    }

    @Provides
    @Singleton
    fun provideProtoDataStoreDataSource(
        settings: DataStore<SettingsDto>,
        chatRoomFilterDto: DataStore<ChatRoomFilterDto>,
        chatRoomShortDto: DataStore<ChatRoomShortDto>
    ): ProtoDataStoreDataSource {
        return ProtoDataStoreDataSourceImpl(settings, chatRoomFilterDto, chatRoomShortDto)
    }

}