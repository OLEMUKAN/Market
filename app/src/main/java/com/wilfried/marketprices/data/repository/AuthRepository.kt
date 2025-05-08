package com.wilfried.marketprices.data.repository

import com.google.firebase.auth.FirebaseUser
import com.wilfried.marketprices.data.model.User
import com.wilfried.marketprices.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface for authentication operations
 */
interface AuthRepository {
    /**
     * Get the current authenticated user
     * @return The FirebaseUser or null if not authenticated
     */
    fun getCurrentUser(): FirebaseUser?
    
    /**
     * Sign in with email and password
     * @param email User's email
     * @param password User's password
     * @return Flow emitting Resource states for the login process
     */
    fun signIn(email: String, password: String): Flow<Resource<FirebaseUser>>
    
    /**
     * Register a new user with email, password, and name
     * @param name User's name
     * @param email User's email
     * @param password User's password
     * @return Flow emitting Resource states for the registration process
     */
    fun register(name: String, email: String, password: String): Flow<Resource<FirebaseUser>>
    
    /**
     * Sign out the current user
     */
    fun signOut()
} 