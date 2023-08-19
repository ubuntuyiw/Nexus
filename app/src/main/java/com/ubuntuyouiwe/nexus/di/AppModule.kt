package com.ubuntuyouiwe.nexus.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.util.Assets
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
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
        ignoreUnknownKeys = false
        isLenient = false
        prettyPrint = false
        allowStructuredMapKeys = true
    }


}


