package com.ubuntuyouiwe.nexus.data.source.remote.firebase

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.AggregateQuerySnapshot
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.messaging.FirebaseMessaging
import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.util.CloudFunctions
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.dto_type.CommonCollectionFields
import com.ubuntuyouiwe.nexus.data.util.dto_type.messages.MessagesFields
import com.ubuntuyouiwe.nexus.data.util.toHashMap
import com.ubuntuyouiwe.nexus.data.util.toUserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
    private val functions: FirebaseFunctions,
    private val googleSignInClient: GoogleSignInClient
) : FirebaseDataSource {
    override suspend fun signUp(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
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


    override suspend fun googleSignIn(data: Intent): AuthResult {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return auth.signInWithCredential(credential).await()
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

    override fun userState(): UserDto? {
        return auth.currentUser?.toUserDto()
    }

    override fun userStateListener(): Flow<UserDto?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.toUserDto())
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }


    override suspend fun getAllDocumentListener(database: FirebaseCollections): Flow<Result<QuerySnapshot>> =
        callbackFlow {
            val registration = fireStore.collection(database.name)
                .whereEqualTo(CommonCollectionFields.OWNER_ID.key, auth.currentUser?.toUserDto()?.uid)
                .orderBy(CommonCollectionFields.LAST_MESSAGE_DATE.key, Query.Direction.DESCENDING)
                .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                    error?.let {
                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    it.localizedMessage ?: "Unknown",
                                    it.code
                                )
                            )
                        )
                    }
                    value?.let {
                        trySend(Result.success(it))
                    } ?: run {

                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    "No data found for the requested document",
                                    FirebaseFirestoreException.Code.NOT_FOUND
                                )
                            )
                        )
                    }

                }
            awaitClose { registration.remove() }
        }

    override suspend fun getDocumentListener(
        database: FirebaseCollections,
        id: String
    ): Flow<Result<QuerySnapshot>> =
        callbackFlow {
            val registration = fireStore.collection(database.name)
                .whereEqualTo(CommonCollectionFields.ID.key, id).whereEqualTo(
                    CommonCollectionFields.OWNER_ID.key,
                    auth.currentUser?.toUserDto()?.uid
                )
                .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                    error?.let {
                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    it.localizedMessage ?: "Unknown",
                                    it.code
                                )
                            )
                        )
                    }
                    value?.let {
                        trySend(Result.success(it))
                    } ?: run {

                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    "No data found for the requested document",
                                    FirebaseFirestoreException.Code.NOT_FOUND
                                )
                            )
                        )
                    }

                }
            awaitClose { registration.remove() }
        }

    override suspend fun getAllSubCollectionDocumentListener(
        database: FirebaseCollections,
        id: String
    ): Flow<Result<QuerySnapshot>> = callbackFlow {
        val registration =
            fireStore.collection(database.name).document(id)
                .collection(FirebaseCollections.Messages.name)
                .orderBy(MessagesFields.CREATED.key, Query.Direction.DESCENDING)
                .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                    error?.let {
                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    it.localizedMessage ?: "Unknown",
                                    it.code
                                )
                            )
                        )
                    }
                    value?.let {
                        trySend(Result.success(it))
                    } ?: kotlin.run {

                        trySendBlocking(
                            Result.failure(
                                FirebaseFirestoreException(
                                    "No data found for the requested document",
                                    FirebaseFirestoreException.Code.NOT_FOUND
                                )
                            )
                        )
                    }

                }
        awaitClose { registration.remove() }

    }

    override suspend fun ai(data: AIRequest): HttpsCallableResult? {
        val dataToHashMap = data.toHashMap()
        return functions.getHttpsCallable(CloudFunctions.AI.key).call(dataToHashMap).await()
    }

    override fun documentToSubCollection(
        database: FirebaseCollections,
        document: String,
        subDataBases: FirebaseCollections
    ): CollectionReference {
        return fireStore.collection(database.name).document(document).collection(subDataBases.name)
    }

    override suspend fun getAllDocument(database: FirebaseCollections): QuerySnapshot {
        return fireStore.collection(database.name).get().await()
    }

    override suspend fun getDocument(
        database: FirebaseCollections,
        document: String
    ): QuerySnapshot {

        return fireStore.collection(database.name)
            .whereEqualTo(CommonCollectionFields.ID.key, document).get().await()
    }

    override suspend fun set(
        collection: FirebaseCollections,
        id: String,
        data: HashMap<String, Any?>
    ): Void? {

        return fireStore.collection(collection.name).document(id).set(data, SetOptions.merge())
            .await()
    }

    override suspend fun batchSet(
        collection: FirebaseCollections,
        docsAndData: HashMap<String, HashMap<String, Any?>>
    ): Void? {
        val batch = fireStore.batch()

        for ((docId, data) in docsAndData) {
            val docRef = fireStore.collection(collection.name).document(docId)
            batch.set(docRef, data, SetOptions.merge())
        }
        return batch.commit().await()
    }

    override suspend fun batchDelete(
        collection: FirebaseCollections,
        ids: List<String>
    ): Void? {
        val batch = fireStore.batch()

        for (docId in ids) {
            val docRef = fireStore.collection(collection.name).document(docId)
            batch.delete(docRef)
        }

        return batch.commit().await()
    }

    override suspend fun batchDeleteWithSubCollections(
        collection: FirebaseCollections,
        ids: List<String>
    ): Void? {
        val batch = fireStore.batch()

        for (docId in ids) {
            val docRef = fireStore.collection(collection.name).document(docId)

            val subCollectionRef = docRef.collection(FirebaseCollections.Messages.name)
            val subDocs = subCollectionRef.get().await().documents
            for (subDoc in subDocs) {
                batch.delete(subDoc.reference)
            }
            batch.delete(docRef)
        }

        return batch.commit().await()
    }

    override suspend fun isDocumentExist(
        collection: FirebaseCollections,
        documentId: String
    ): Boolean {
        val docRef = fireStore.collection(collection.name).document(documentId)
        return docRef.get().await().exists()
    }

    suspend fun count(collection: FirebaseCollections): AggregateQuerySnapshot? {
        return fireStore.collection(collection.name).count().get((AggregateSource.SERVER)).await()
    }


    override suspend fun addSubCollection(
        collection: FirebaseCollections,
        documentId: String,
        subCollection: FirebaseCollections,
        data: HashMap<String, Any?>
    ): DocumentReference {
        return fireStore.collection(collection.name).document(documentId)
            .collection(subCollection.name).add(data).await()
    }


}