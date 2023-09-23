package com.ubuntuyouiwe.nexus.di.singleton

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.PurchasesUpdatedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.manager.BillingManagerImpl
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.Assets
import com.ubuntuyouiwe.nexus.di.GoogleClientId
import com.ubuntuyouiwe.nexus.di.RecognizerDefault
import com.ubuntuyouiwe.nexus.domain.manager.BillingManager
import com.ubuntuyouiwe.nexus.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @GoogleClientId
    @Provides
    fun provideGoogleClientId(): String {
        return Constant.WEB_CLIENT_ID
    }

    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        @GoogleClientId clientId: String,
    ): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }


    @Provides
    @Singleton
    fun provideRoles(@ApplicationContext context: Context): List<RoleDto> {
        val files = listOf(Assets.DebateArena.name + ".json", Assets.NeutralMode.name + ".json")
        return files.map { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<RoleDto>(jsonString)
        }
    }

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = false
            prettyPrint = false
            allowStructuredMapKeys = true
        }

    @RecognizerDefault
    @Provides
    @Singleton
    fun provideRecognizerDefault(): TextRecognizer {
        return  TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    @Singleton
    fun provideBillingClient(@ApplicationContext context: Context): BillingClient.Builder {
        return BillingClient.newBuilder(context)
    }











}


