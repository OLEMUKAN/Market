package com.wilfried.marketprices.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wilfried.marketprices.data.repository.AuthRepository
import com.wilfried.marketprices.data.repository.AuthRepositoryImpl
import com.wilfried.marketprices.data.repository.CommodityRepository
import com.wilfried.marketprices.data.repository.CommodityRepositoryImpl

/**
 * Simple service locator for dependency injection
 */
object ServiceLocator {
    // Firebase instances
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    
    // Repositories
    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(firebaseAuth, firestore)
    }
    
    private val commodityRepository: CommodityRepository by lazy {
        CommodityRepositoryImpl(firestore)
    }
    
    // Public accessors
    fun provideAuthRepository(): AuthRepository = authRepository
    
    fun provideCommodityRepository(): CommodityRepository = commodityRepository
} 