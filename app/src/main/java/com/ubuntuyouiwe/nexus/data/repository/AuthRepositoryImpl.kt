package com.ubuntuyouiwe.nexus.data.repository

import android.content.Intent
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.dto.user_messaging_data.UserMessagingDataDto
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.dto_type.user.UserDtoFields
import com.ubuntuyouiwe.nexus.data.util.toHashMap
import com.ubuntuyouiwe.nexus.data.util.toUser
import com.ubuntuyouiwe.nexus.data.util.toUserDto
import com.ubuntuyouiwe.nexus.data.util.toUserMessagingData
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.domain.model.UserCredentials
import com.ubuntuyouiwe.nexus.domain.model.roles.PurposeSelection
import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData
import com.ubuntuyouiwe.nexus.domain.repository.AuthRepository
import com.ubuntuyouiwe.nexus.domain.util.toPurposeSelectionDto
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
        createUserDatabase(result.user?.uid, result.user?.email, result.user?.displayName)
        firebaseDatasource.createUserMessagingData()
    }

    override suspend fun googleSignIn(data: Intent): User? {
        val authResult = firebaseDatasource.googleSignIn(data)

        if (authResult.additionalUserInfo?.isNewUser == true) {
            createUserDatabase(
                authResult.user?.uid,
                authResult.user?.email,
                authResult.user?.displayName
            )
            firebaseDatasource.createUserMessagingData()
        }
        return authResult.user?.toUserDto()?.toUser()
    }

    override suspend fun signIn(userCredentials: UserCredentials): User? {
        val userCredentialsDto = userCredentials.toUserCredentialsDto()
        val result =
            firebaseDatasource.loginIn(userCredentialsDto.email, userCredentialsDto.password)
        return result.user?.toUserDto()?.toUser()
    }

    override suspend fun loginUserDatabase(uid: String?) {
        uid?.let {
            val data = hashMapOf<String, Any?>(UserDtoFields.SHOULD_LOGOUT.key to false)
            firebaseDatasource.set(
                FirebaseCollections.Users, it, data
            )
        }
    }


    private suspend fun createUserDatabase(uid: String?, email: String?, name: String?) {
        uid?.let {
            val data = UserDto(
                email = email,
                displayName = name,
                uid = it,
                ownerId = it,
                id = it,
                shouldLogout = false,
                isOnBoarding = true
            )
            firebaseDatasource.set(
                FirebaseCollections.Users, it, data.toHashMap()
            )
        }
    }

    override suspend fun getUserListener(id: String): Flow<User?> {
        return firebaseDatasource.getDocumentListener(FirebaseCollections.Users, id).map {
            if (it.isSuccess) {
                val userDto =
                    it.getOrNull()?.documents?.firstOrNull()?.toObject(UserDto::class.java)
                val addIsFromCache = userDto?.copy(
                    isFromCache = it.getOrNull()?.metadata?.isFromCache,
                    hasPendingWrites = it.getOrNull()?.metadata?.hasPendingWrites()
                )
                addIsFromCache?.toUser()

            } else {
                throw it.exceptionOrNull()!!
            }
        }

    }

    override suspend fun updatePurposeSelection(purposeSelection: PurposeSelection) {
        val uid = firebaseDatasource.userState()?.uid
        val purposeSelectionDtoHashMap = purposeSelection.toPurposeSelectionDto().toHashMap()
        val data = hashMapOf<String, Any?>("purposeSelection" to purposeSelectionDtoHashMap)
        if (uid != null) {
            firebaseDatasource.set(
                FirebaseCollections.Users,
                uid,
                data
            )
        } else {
            throw Exception("No active user found")
        }
    }

    override suspend fun updateSystemMessage(systemMessage: String) {
        val uid = firebaseDatasource.userState()?.uid
        val dataName = hashMapOf<String, Any?>(
            UserDtoFields.SYSTEM_MESSAGE.key to systemMessage,
            UserDtoFields.IS_ON_BOARDING.key to false
        )
        if (uid != null) {
            firebaseDatasource.set(
                FirebaseCollections.Users,
                uid,
                dataName
            )
        } else {
            throw Exception("No active user found")
        }
    }

    override suspend fun changePassword(password: String) {
        firebaseDatasource.changePassword(password)
    }


    override suspend fun updateDisplayName(name: String) {
        val uid = firebaseDatasource.userState()?.uid
        val dataName = hashMapOf<String, Any?>(UserDtoFields.DISPLAY_NAME.key to name)
        if (uid != null) {
            firebaseDatasource.updateDisplayName(name)
            firebaseDatasource.set(
                FirebaseCollections.Users,
                uid,
                dataName
            )
        } else {
            throw Exception("No active user found")
        }

    }

    override suspend fun getUserMessagingData(id: String): Flow<UserMessagingData?> {
        return firebaseDatasource.getDocumentListener(FirebaseCollections.UserMessagingData, id)
            .map {
                if (it.isSuccess) {
                    val userDto =
                        it.getOrNull()?.documents?.firstOrNull()
                            ?.toObject(UserMessagingDataDto::class.java)
                    val addIsFromCache = userDto?.copy(
                        isFromCache = it.getOrNull()?.metadata?.isFromCache,
                        hasPendingWrites = it.getOrNull()?.metadata?.hasPendingWrites()
                    )
                    addIsFromCache?.toUserMessagingData()

                } else {
                    throw it.exceptionOrNull()!!
                }
            }
    }


    override fun googleSignInIntent(): Intent {
        return firebaseDatasource.signInIntent()
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