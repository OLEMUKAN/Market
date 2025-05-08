package com.wilfried.marketprices.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.wilfried.marketprices.data.repository.AuthRepository
import com.wilfried.marketprices.utils.Resource
import com.wilfried.marketprices.utils.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel for authentication-related operations
 */
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    // Form fields
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    
    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    // Error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    
    // Authentication state
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated
    
    // Current user
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    init {
        checkAuthStatus()
    }

    /**
     * Update email field
     */
    fun updateEmail(value: String) {
        _email.value = value
    }
    
    /**
     * Update password field
     */
    fun updatePassword(value: String) {
        _password.value = value
    }
    
    /**
     * Update name field
     */
    fun updateName(value: String) {
        _name.value = value
    }
    
    /**
     * Clear all form fields
     */
    fun clearFields() {
        _email.value = ""
        _password.value = ""
        _name.value = ""
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Check the current authentication status
     */
    fun checkAuthStatus() {
        _currentUser.value = authRepository.getCurrentUser()
        _isAuthenticated.value = _currentUser.value != null
    }

    /**
     * Attempt to sign in with email and password
     */
    fun signIn() {
        // Validate input
        if (!validateLoginInput()) {
            return
        }
        
        _isLoading.value = true
        _errorMessage.value = null
        
        authRepository.signIn(_email.value, _password.value)
            .onEach { result ->
                _isLoading.value = false
                
                when (result) {
                    is Resource.Success -> {
                        _currentUser.value = result.data
                        _isAuthenticated.value = true
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                    }
                    Resource.Loading -> {
                        // Already handled by setting isLoading to true
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Register a new user
     */
    fun register() {
        // Validate input
        if (!validateRegistrationInput()) {
            return
        }
        
        _isLoading.value = true
        _errorMessage.value = null
        
        authRepository.register(_name.value, _email.value, _password.value)
            .onEach { result ->
                _isLoading.value = false
                
                when (result) {
                    is Resource.Success -> {
                        _currentUser.value = result.data
                        _isAuthenticated.value = true
                    }
                    is Resource.Error -> {
                        _errorMessage.value = result.message
                    }
                    Resource.Loading -> {
                        // Already handled by setting isLoading to true
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * Sign out the current user
     */
    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _isAuthenticated.value = false
        clearFields()
        clearError()
    }
    
    /**
     * Validate login input fields
     */
    private fun validateLoginInput(): Boolean {
        if (_email.value.isBlank()) {
            _errorMessage.value = "Email is required"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _errorMessage.value = "Invalid email format"
            return false
        }
        
        if (_password.value.isBlank()) {
            _errorMessage.value = "Password is required"
            return false
        }
        
        if (_password.value.length < 6) {
            _errorMessage.value = "Password must be at least 6 characters"
            return false
        }
        
        return true
    }
    
    /**
     * Validate registration input fields
     */
    private fun validateRegistrationInput(): Boolean {
        if (_name.value.isBlank()) {
            _errorMessage.value = "Name is required"
            return false
        }
        
        // Also validate email and password
        return validateLoginInput()
    }

    /**
     * Factory for creating AuthViewModel instances
     */
    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(ServiceLocator.provideAuthRepository()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 