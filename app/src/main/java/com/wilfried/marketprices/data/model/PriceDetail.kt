package com.wilfried.marketprices.data.model

import com.google.firebase.firestore.PropertyName

/**
 * Data class representing price details for a commodity in a specific district
 * Contains the Farm Gate Price (FGP) and Market Price (MP)
 */
data class PriceDetail(
    @PropertyName("fgp")
    val farmGatePrice: Double = 0.0,
    
    @PropertyName("mp")
    val marketPrice: Double = 0.0
) {
    constructor() : this(0.0, 0.0) // No-arg constructor for Firestore
} 