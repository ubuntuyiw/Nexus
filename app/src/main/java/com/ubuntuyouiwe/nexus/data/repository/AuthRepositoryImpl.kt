package com.ubuntuyouiwe.nexus.data.repository

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import com.ubuntuyouiwe.nexus.data.source.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.toUser
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.util.toUserCredentialsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseDatasource: FirebaseDataSource,
) : AuthRepository {

    override suspend fun signUp(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        firebaseDatasource.signUp(userCredentialsDto.email, userCredentialsDto.password)
    }

    override suspend fun googleSignIn(data: Intent): FirebaseUser? {
        return firebaseDatasource.googleSignIn(data)
    }


    override fun googleSignInIntent(): Intent {
        return firebaseDatasource.signInIntent()
    }


    override suspend fun signIn(userCredentials: UserCredentials) {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        firebaseDatasource.loginIn(userCredentialsDto.email, userCredentialsDto.password)
    }

    override suspend fun logOut() {
        firebaseDatasource.googleSignOut()
        firebaseDatasource.signOut()

    }

    override suspend fun sendPasswordResetEmail(email: String) {
        firebaseDatasource.sendPasswordResetEmail(email)
    }

    override fun listenUserOnlineStatus(): Flow<User?> {
        return firebaseDatasource.userStateListener().map { userDto ->
            userDto?.toUser()
        }

    }


}