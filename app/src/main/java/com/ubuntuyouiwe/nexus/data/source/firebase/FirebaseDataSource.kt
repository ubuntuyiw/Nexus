package com.ubuntuyouiwe.nexus.data.source.firebase

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.data.dto.UserDto
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    suspend fun signUp(email: String, password: String)

    suspend fun loginIn(email: String, password: String)

    suspend fun googleSignIn(data: Intent): FirebaseUser?

    suspend fun googleSignOut()
    suspend fun sendPasswordResetEmail(email: String)
    fun signInIntent(): Intent
    fun signOut()

    fun userStateListener(): Flow<UserDto?>
}