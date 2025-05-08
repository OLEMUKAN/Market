package com.wilfried.marketprices.navigation

/**
 * Sealed class representing the app's screens/routes
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Detail : Screen("detail/{commodityId}") {
        fun createRoute(commodityId: String) = "detail/$commodityId"
    }
} 