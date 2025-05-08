package com.wilfried.marketprices.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wilfried.marketprices.data.model.Commodity
import com.wilfried.marketprices.data.repository.CommodityRepository
import com.wilfried.marketprices.utils.Resource
import com.wilfried.marketprices.utils.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the home screen
 */
class HomeViewModel(
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchCommodities()
    }

    /**
     * Fetch all commodities from the repository
     */
    private fun fetchCommodities() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        commodityRepository.getAllCommodities()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                commodities = result.data ?: emptyList(),
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Try to fetch commodities again
     */
    fun refreshCommodities() {
        fetchCommodities()
    }

    /**
     * UI state for the home screen
     */
    data class HomeUiState(
        val isLoading: Boolean = false,
        val commodities: List<Commodity> = emptyList(),
        val error: String? = null
    )

    /**
     * Factory for creating HomeViewModel instances
     */
    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(
                    ServiceLocator.provideCommodityRepository()
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 