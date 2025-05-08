package com.wilfried.marketprices

import android.app.Application
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.FirebaseApp

class MarketPricesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Check Google Play Services availability
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != com.google.android.gms.common.ConnectionResult.SUCCESS) {
            // Log the error but don't attempt to fix it here - this will be handled in activities
            android.util.Log.w("MarketPricesApp", "Google Play Services not available: $resultCode")
        }
    }
} 