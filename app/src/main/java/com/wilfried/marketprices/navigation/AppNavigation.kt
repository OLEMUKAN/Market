package com.wilfried.marketprices.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wilfried.marketprices.ui.screens.DetailScreen
import com.wilfried.marketprices.ui.screens.HomeScreen
import com.wilfried.marketprices.ui.screens.LoginScreen
import com.wilfried.marketprices.ui.screens.RegisterScreen
import com.wilfried.marketprices.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory())
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    
    // Determine the start destination based on login status
    val startDestination = if (currentUser != null) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login Screen
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Register Screen
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Home Screen
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToDetail = { commodityId ->
                    navController.navigate(Screen.Detail.createRoute(commodityId))
                }
            )
        }
        
        // Detail Screen
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("commodityId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val commodityId = backStackEntry.arguments?.getString("commodityId") ?: ""
            DetailScreen(
                commodityId = commodityId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 