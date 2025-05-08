package com.wilfried.marketprices.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.wilfried.marketprices.data.model.Commodity
import com.wilfried.marketprices.data.model.PriceDetail
import com.wilfried.marketprices.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Implementation of CommodityRepository that fetches data from Firestore
 */
class CommodityRepositoryImpl(
    private val firestore: FirebaseFirestore
) : CommodityRepository {

    companion object {
        private const val COLLECTION_COMMODITIES = "commodities"
    }

    override fun getAllCommodities(): Flow<Resource<List<Commodity>>> = callbackFlow {
        trySend(Resource.Loading)
        
        val listenerRegistration = firestore.collection(COLLECTION_COMMODITIES)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(Resource.Error(exception.message ?: "Unknown error occurred"))
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    try {
                        val commodities = snapshot.documents.map { document ->
                            val data = document.data
                            val id = document.id
                            val name = data?.get("name") as? String ?: ""
                            val imageUrl = data?.get("imageUrl") as? String ?: ""
                            
                            // Parse prices map from Firestore
                            val pricesMap = data?.get("prices") as? Map<String, Map<String, Any>> ?: emptyMap()
                            val prices = pricesMap.mapValues { (_, priceData) ->
                                val fgp = (priceData["fgp"] as? Number)?.toDouble() ?: 0.0
                                val mp = (priceData["mp"] as? Number)?.toDouble() ?: 0.0
                                PriceDetail(farmGatePrice = fgp, marketPrice = mp)
                            }
                            
                            Commodity(id, name, imageUrl, prices)
                        }
                        
                        trySend(Resource.Success(commodities))
                    } catch (e: Exception) {
                        trySend(Resource.Error(e.message ?: "Error parsing commodity data"))
                    }
                } else {
                    trySend(Resource.Success(emptyList()))
                }
            }
        
        awaitClose { listenerRegistration.remove() }
    }

    override fun getCommodityById(id: String): Flow<Resource<Commodity>> = callbackFlow {
        trySend(Resource.Loading)
        
        val listenerRegistration = firestore.collection(COLLECTION_COMMODITIES)
            .document(id)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(Resource.Error(exception.message ?: "Unknown error occurred"))
                    return@addSnapshotListener
                }
                
                if (snapshot != null && snapshot.exists()) {
                    try {
                        val data = snapshot.data
                        val name = data?.get("name") as? String ?: ""
                        val imageUrl = data?.get("imageUrl") as? String ?: ""
                        
                        // Parse prices map from Firestore
                        val pricesMap = data?.get("prices") as? Map<String, Map<String, Any>> ?: emptyMap()
                        val prices = pricesMap.mapValues { (_, priceData) ->
                            val fgp = (priceData["fgp"] as? Number)?.toDouble() ?: 0.0
                            val mp = (priceData["mp"] as? Number)?.toDouble() ?: 0.0
                            PriceDetail(farmGatePrice = fgp, marketPrice = mp)
                        }
                        
                        val commodity = Commodity(id, name, imageUrl, prices)
                        trySend(Resource.Success(commodity))
                    } catch (e: Exception) {
                        trySend(Resource.Error(e.message ?: "Error parsing commodity data"))
                    }
                } else {
                    trySend(Resource.Error("Commodity not found"))
                }
            }
        
        awaitClose { listenerRegistration.remove() }
    }
} 