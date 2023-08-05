package com.ubuntuyouiwe.nexus.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.nexus.data.source.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.source.firebase.FirebaseDataSourceImpl
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
        googleClientId: GoogleSignInClient
    ): FirebaseDataSource {

        return FirebaseDataSourceImpl(fireStore, firebaseAuth, firebaseMessaging, googleClientId)
    }

}