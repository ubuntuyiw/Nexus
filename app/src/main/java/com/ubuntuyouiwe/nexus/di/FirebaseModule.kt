package com.ubuntuyouiwe.nexus.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return  Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore =
        Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging =
        Firebase.messaging

    @Provides
    @Singleton
    fun provideFirebaseMFunctions(): FirebaseFunctions =
        Firebase.functions

    @Provides
    @Singleton
    fun provideFirebaseLanguage(): LanguageIdentifier =
        LanguageIdentification.getClient()

}