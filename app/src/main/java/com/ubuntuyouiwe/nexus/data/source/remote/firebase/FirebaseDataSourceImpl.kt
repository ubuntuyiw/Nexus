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
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.toAny
import com.ubuntuyouiwe.nexus.data.util.toUserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

class FirebaseDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
    private val functions: FirebaseFunctions,
    private val googleSignInClient: GoogleSignInClient,
    private val json: Json
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
                .orderBy("creationDate", Query.Direction.DESCENDING)
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

    override suspend fun getDocumentListener(
        database: FirebaseCollections,
        id: String
    ): Flow<Result<QuerySnapshot>> =
        callbackFlow {
            val registration = fireStore.collection(database.name)
                .whereEqualTo("id", id)
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

    override suspend fun getAllSubCollectionDocumentListener(
        database: FirebaseCollections,
        id: String
    ): Flow<Result<QuerySnapshot>> = callbackFlow {
        val registration =
            fireStore.collection(database.name).document(id)
                .collection(FirebaseCollections.Messages.name)
                .orderBy("created", Query.Direction.DESCENDING)
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
        val jsonString = json.encodeToString(data)
        val map: Map<String, Any?> = Json.decodeFromString(
            MapSerializer(String.serializer(), JsonElement.serializer()),
            jsonString
        )
            .mapValues { (_, value) -> value.toAny() }
        return functions.getHttpsCallable("ai").call(map).await()
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

        return fireStore.collection(database.name).whereEqualTo("id", document).get().await()
    }

    override suspend fun set(
        collection: FirebaseCollections,
        id: String,
        data: HashMap<String, Any?>
    ): Void? {
        return fireStore.collection(collection.name).document(id).set(data, SetOptions.merge())
            .await()
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