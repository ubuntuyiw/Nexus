package com.ubuntuyouiwe.nexus.data.source.firebase

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.nexus.data.dto.UserDto
import com.ubuntuyouiwe.nexus.data.util.toUserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
    private val googleSignInClient: GoogleSignInClient
) : FirebaseDataSource {
    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun loginIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    init {
        val fireStoreSettings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(
                PersistentCacheSettings.newBuilder()
                    .setSizeBytes(1024L * 1024L * 100L) // 100 MB
                    .build()
            )
            .build()
        fireStore.firestoreSettings = fireStoreSettings
    }


    override suspend fun googleSignIn(data: Intent): FirebaseUser? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        return auth.signInWithCredential(credential).await().user
    }

    override suspend fun googleSignOut() {
        googleSignInClient.signOut().await()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override fun signInIntent(): Intent {
        return googleSignInClient.signInIntent
    }


    override fun signOut() {
        auth.signOut()
    }

    override fun userStateListener(): Flow<UserDto?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.toUserDto())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

}