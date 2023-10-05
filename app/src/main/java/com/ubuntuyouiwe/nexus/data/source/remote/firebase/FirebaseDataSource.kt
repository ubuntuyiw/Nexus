package com.ubuntuyouiwe.nexus.data.source.remote.firebase

import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.functions.HttpsCallableResult
import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData
import kotlinx.coroutines.flow.Flow

interface FirebaseDataSource {

    suspend fun signUp(email: String, password: String): AuthResult

    suspend fun loginIn(email: String, password: String): AuthResult

    suspend fun googleSignIn(data: Intent): AuthResult

    suspend fun googleSignOut()
    suspend fun sendPasswordResetEmail(email: String)
    fun signInIntent(): Intent
    fun signOut()

    suspend fun updateDisplayName(name: String)

    suspend fun changePassword(password: String)

    fun userStateListener(): Flow<UserDto?>

/*    suspend fun getAllDocument(database: FirebaseCollections): QuerySnapshot

    suspend fun getDocument(database: FirebaseCollections, document: String): QuerySnapshot*/
    suspend fun getAllDocumentListener(database: FirebaseCollections): Flow<Result<QuerySnapshot>>
    fun userState(): UserDto?
    suspend fun set(
        collection: FirebaseCollections,
        id: String,
        data: HashMap<String, Any?>
    ): Void?
    suspend fun batchSet(
        collection: FirebaseCollections,
        docsAndData: HashMap<String, HashMap<String, Any?>>
    ): Void?

 /*   suspend fun batchDelete(
        collection: FirebaseCollections,
        ids: List<String>
    ): Void?*/

    suspend fun batchDeleteWithSubCollections(
        collection: FirebaseCollections,
        ids: List<String>
    ): Void?
    suspend fun addSubCollection(
        collection: FirebaseCollections,
        documentId: String,
        subCollection: FirebaseCollections,
        data: HashMap<String, Any?>
    ): DocumentReference
    suspend fun getDocumentListener(database: FirebaseCollections, id: String): Flow<Result<QuerySnapshot>>
    suspend fun getAllSubCollectionDocumentListener(database: FirebaseCollections, id: String): Flow<Result<QuerySnapshot>>

    suspend fun ai(data: AIRequest): HttpsCallableResult?

    suspend fun handlePurchase(data: HashMap<String, String?>): HttpsCallableResult?

    suspend fun createUserMessagingData(): HttpsCallableResult?

/*    suspend fun isDocumentExist(collection: FirebaseCollections, documentId: String): Boolean
    fun documentToSubCollection(database: FirebaseCollections, document: String, subDataBases: FirebaseCollections): CollectionReference*/
}