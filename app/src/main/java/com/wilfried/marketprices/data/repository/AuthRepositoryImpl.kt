package com.wilfried.marketprices.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.wilfried.marketprices.data.model.User
import com.wilfried.marketprices.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Implementation of the AuthRepository interface
 */
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun signIn(email: String, password: String): Flow<Resource<FirebaseUser>> = callbackFlow {
        trySend(Resource.Loading)
        
        try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let { user ->
                trySend(Resource.Success(user))
            } ?: trySend(Resource.Error("Authentication failed"))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: "An unknown error occurred"))
        }
        
        awaitClose()
    }

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> = callbackFlow {
        trySend(Resource.Loading)
        
        try {
            // Create authentication account
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            
            // Set the display name
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
                
            authResult.user?.let { user ->
                user.updateProfile(profileUpdates).await()
                
                // Create user document in Firestore
                val userModel = User(
                    id = user.uid,
                    name = name,
                    email = email
                )
                
                firestore.collection("users")
                    .document(user.uid)
                    .set(userModel)
                    .await()
                
                trySend(Resource.Success(user))
            } ?: trySend(Resource.Error("Registration failed"))
        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: "An unknown error occurred"))
        }
        
        awaitClose()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
} 