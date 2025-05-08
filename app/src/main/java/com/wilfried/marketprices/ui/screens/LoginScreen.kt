package com.wilfried.marketprices.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wilfried.marketprices.R
import com.wilfried.marketprices.ui.components.ErrorMessage
import com.wilfried.marketprices.ui.components.LoadingIndicator
import com.wilfried.marketprices.ui.components.PrimaryButton
import com.wilfried.marketprices.ui.components.TextInput
import com.wilfried.marketprices.viewmodel.AuthViewModel

/**
 * Login screen component
 */
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory())
) {
    // Collect state from ViewModel
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    
    // Local UI state
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    
    // Handle successful authentication
    if (isAuthenticated) {
        onNavigateToHome()
        return
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.login_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xlarge)))
            
            // Email input
            TextInput(
                value = email,
                onValueChange = { authViewModel.updateEmail(it) },
                labelText = stringResource(id = R.string.email),
                leadingIcon = Icons.Default.Email,
                isError = errorMessage != null && email.isBlank(),
                errorMessage = if (errorMessage != null && email.isBlank()) 
                    stringResource(id = R.string.email_required) else null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            
            // Password input
            TextInput(
                value = password,
                onValueChange = { authViewModel.updatePassword(it) },
                labelText = stringResource(id = R.string.password),
                leadingIcon = Icons.Default.Lock,
                isPassword = !passwordVisible,
                isError = errorMessage != null && password.isBlank(),
                errorMessage = if (errorMessage != null && password.isBlank()) 
                    stringResource(id = R.string.password_required) else null,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            text = if (passwordVisible) "Hide" else "Show",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        authViewModel.signIn()
                    }
                )
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            
            // Error message
            if (errorMessage != null) {
                ErrorMessage(message = errorMessage!!)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            }
            
            // Login button
            PrimaryButton(
                text = stringResource(id = R.string.login),
                onClick = { authViewModel.signIn() },
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
            )
            
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            
            // Register link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.dont_have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = {
                        // Clear form fields and errors
                        authViewModel.clearError()
                        // Navigate to register screen
                        onNavigateToRegister()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Loading indicator
            if (isLoading) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                LoadingIndicator()
            }
        }
    }
} 