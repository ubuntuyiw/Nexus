package com.ubuntuyouiwe.nexus.domain.repository

import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendPasswordResetEmail(email: String)
    suspend fun signUp(userCredentials: UserCredentials)

    suspend fun signIn(userCredentials: UserCredentials)

    suspend fun logOut()

    suspend fun googleSignIn(data: Intent): AuthResult?

    fun googleSignInIntent(): Intent

    fun listenUserOnlineStatus(): Flow<User?>
}