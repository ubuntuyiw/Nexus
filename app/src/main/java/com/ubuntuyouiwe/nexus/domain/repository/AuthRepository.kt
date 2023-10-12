package com.ubuntuyouiwe.nexus.domain.repository

import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection
import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun signUp(userCredentials: UserCredentials)

    suspend fun signIn(userCredentials: UserCredentials): User?
    suspend fun loginUserDatabase(uid: String?)

    suspend fun getUserMessagingData(id: String): Flow<UserMessagingData?>
    suspend fun logOut()

    suspend fun googleSignIn(data: Intent): User?

    fun googleSignInIntent(): Intent

    fun listenUserOnlineStatus(): Flow<User?>

    suspend fun getUserListener(id: String): Flow<User?>

    suspend fun updateDisplayName(name: String)

    suspend fun updatePurposeSelection(purposeSelection: PurposeSelection)

    suspend fun updateSystemMessage(systemMessage: String)

    suspend fun changePassword(password: String)

    suspend fun setSystemLanguage(code: String)

    suspend fun saveTokenToDatabase(onNewToken: String?)

    suspend fun removeTokenFromDatabase(tokenToRemove: String)

    suspend fun getDeviceToken(): String?
}