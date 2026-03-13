package com.example.cashcactus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashCactusApp()
        }
    }
}

@Composable
fun CashCactusApp(viewModel: MainViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"   // 🔥 CHANGED FROM "register"
    ) {

        composable("register") {
            RegisterScreen(navController, viewModel)
        }

        composable("login") {
            LoginScreen(navController, viewModel)
        }

        composable("home") {
            HomeScreen(navController, viewModel)
        }

        composable("help") {
            HelpScreen(navController)
        }

        composable("about") {
            AboutScreen(navController)
        }

        composable("contact") {
            ContactScreen()
        }

        composable("privacy") {
            PrivacyPolicyScreen(navController)
        }

        composable("edit") {
            EditProfileScreen(navController, viewModel)
        }

        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }

        composable(
            route = "expenseCategory/{age}",
            arguments = listOf(
                navArgument("age") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val age = backStackEntry.arguments?.getInt("age") ?: 0

            ExpenseCategoryScreen(
                navController = navController,
                age = age,
                viewModel = viewModel
            )
        }

        composable("expenseGraph") {
            ExpenseGraphScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = "expenseInput/{category}/{age}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("age") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val category =
                backStackEntry.arguments?.getString("category") ?: ""

            val age =
                backStackEntry.arguments?.getInt("age") ?: 0

            ExpenseInputScreen(
                navController = navController,
                category = category,
                age = age
            )
        }
    }
}