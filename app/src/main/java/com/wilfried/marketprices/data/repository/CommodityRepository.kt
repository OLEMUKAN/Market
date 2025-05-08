package com.wilfried.marketprices.data.repository

import com.wilfried.marketprices.data.model.Commodity
import com.wilfried.marketprices.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for commodity-related operations
 */
interface CommodityRepository {
    /**
     * Get all commodities from the data source
     * @return Flow of Resource containing a list of Commodity objects
     */
    fun getAllCommodities(): Flow<Resource<List<Commodity>>>
    
    /**
     * Get a specific commodity by its ID
     * @param id The ID of the commodity to retrieve
     * @return Flow of Resource containing the Commodity object
     */
    fun getCommodityById(id: String): Flow<Resource<Commodity>>
} 