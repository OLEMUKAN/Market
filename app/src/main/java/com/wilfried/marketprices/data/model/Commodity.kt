package com.wilfried.marketprices.data.model

/**
 * Data class representing a commodity with details like name, image, and prices by district
 */
data class Commodity(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val prices: Map<String, PriceDetail> = emptyMap()
) {
    constructor() : this("", "", "", emptyMap()) // No-arg constructor for Firestore
    
    /**
     * Calculate the average market price across all districts
     * @return The average market price or 0.0 if no prices are available
     */
    fun getAverageMarketPrice(): Double {
        if (prices.isEmpty()) return 0.0
        
        val sum = prices.values.sumOf { it.marketPrice }
        return sum / prices.size
    }
} 