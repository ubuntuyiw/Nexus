package com.ubuntuyouiwe.nexus.data.repository

import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.toHashMap
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
        val result =
            firebaseDatasource.signUp(userCredentialsDto.email, userCredentialsDto.password)
        createUserDatabase( result.user?.uid, result.user?.email)
    }

    override suspend fun googleSignIn(data: Intent): AuthResult {
        val authResult = firebaseDatasource.googleSignIn(data)
        if (authResult.additionalUserInfo?.isNewUser == true) {
            createUserDatabase(authResult.user?.uid ,authResult.user?.email)
        }
        return firebaseDatasource.googleSignIn(data)
    }

    private suspend fun createUserDatabase(uid: String?, email: String?) {
        uid?.let {
            val data = UserDto(
                email = email,
                totalCompletionTokens = 0.0,
                totalPromptTokens = 0.0,
                totalTokens = 0.0,
                uid = it
            )
            firebaseDatasource.set(
                FirebaseCollections.Users, it, data.toHashMap()
            )
        }

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