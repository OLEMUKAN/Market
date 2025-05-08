package com.wilfried.marketprices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.wilfried.marketprices.navigation.AppNavigation
import com.wilfried.marketprices.ui.theme.MarketPricesTheme

class MainActivity : ComponentActivity() {
    
    private val PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check for Google Play Services
        checkGooglePlayServices()
        
        enableEdgeToEdge()
        setContent {
            MarketPricesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
    
    private fun checkGooglePlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)?.show()
            } else {
                // Google Play Services is not available and cannot be resolved - handle gracefully
                android.util.Log.e("MainActivity", "This device is not supported for Google Play Services")
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Re-check Play Services availability when activity resumes
        checkGooglePlayServices()
    }
}