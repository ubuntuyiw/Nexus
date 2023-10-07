package com.ubuntuyouiwe.nexus.di.singleton

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.ubuntuyouiwe.nexus.data.dto.TermsOfUseDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.util.Assets
import com.ubuntuyouiwe.nexus.di.GoogleClientId
import com.ubuntuyouiwe.nexus.di.RecognizerDefault
import com.ubuntuyouiwe.nexus.di.SystemMessages
import com.ubuntuyouiwe.nexus.di.TermsOfUse
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
        val files = listOf(
            Assets.NeutralMode.name + ".json",
            Assets.RelationshipCoach.name + ".json",
            Assets.DebateArena.name + ".json",
            Assets.TravelAdvisor.name + ".json",
            Assets.Chef.name + ".json",
            Assets.SportsPolymath.name + ".json",
            Assets.LiteratureTeacher.name + ".json",
            Assets.Philosophy.name + ".json",
            Assets.Astrologer.name + ".json",
            Assets.Lawyer.name + ".json",
            Assets.IslamicScholar.name + ".json",
            Assets.Doctor.name + ".json",
            Assets.Bishop.name + ".json",
            Assets.BiologyTeacher.name + ".json",
            Assets.ChemistryTeacher.name + ".json",
            Assets.EnglishTeacher.name + ".json",
            Assets.GeographyTeacher.name + ".json",
            Assets.HistoryTeacher.name + ".json",
            Assets.MathematicsTeacher.name + ".json",
            Assets.PhysicsTeacher.name + ".json",
            Assets.Psychologist.name + ".json",
            Assets.Veterinarian.name + ".json",
            Assets.SoftwareDeveloper.name + ".json",
        )
        return files.map { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<RoleDto>(jsonString)
        }
    }

    @SystemMessages
    @Provides
    @Singleton
    fun provideSystemMessage(@ApplicationContext context: Context): List<MessageItemDto> {
        val files = listOf(
            Assets.NexusSystem.name + ".json",
        )
        return files.map { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<MessageItemDto>(jsonString)
        }
    }

    @TermsOfUse
    @Provides
    @Singleton
    fun provideTermsOfUse(@ApplicationContext context: Context): List<TermsOfUseDto> {
        val files = listOf(
            Assets.TermsOfUse.name + ".json",
        )
        return files.map { fileName ->
            val inputStream = context.assets.open(fileName)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            Json.decodeFromString<TermsOfUseDto>(jsonString)
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
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    @Singleton
    fun provideBillingClient(@ApplicationContext context: Context): BillingClient.Builder {
        return BillingClient.newBuilder(context)
    }


}


