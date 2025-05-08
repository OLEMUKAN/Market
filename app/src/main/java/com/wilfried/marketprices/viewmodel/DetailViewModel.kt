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
 * ViewModel for the detail screen
 */
class DetailViewModel(
    private val commodityId: String,
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        fetchCommodityDetails()
    }

    /**
     * Fetch commodity details from the repository
     */
    private fun fetchCommodityDetails() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        commodityRepository.getCommodityById(commodityId)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                commodity = result.data,
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
     * UI state for the detail screen
     */
    data class DetailUiState(
        val isLoading: Boolean = false,
        val commodity: Commodity? = null,
        val error: String? = null
    )

    /**
     * Factory for creating DetailViewModel instances
     */
    class Factory(private val commodityId: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(
                    commodityId,
                    ServiceLocator.provideCommodityRepository()
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 