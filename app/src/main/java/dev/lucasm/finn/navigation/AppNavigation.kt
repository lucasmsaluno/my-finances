package dev.lucasm.finn.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lucasm.finn.ui.screens.Home
import dev.lucasm.finn.ui.screens.Transaction
import dev.lucasm.finn.viewmodels.TransactionsViewModel

@Composable
fun AppNavigation (
    viewModel: TransactionsViewModel,
    isThemeButtonClicked: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen) {
        composable<Screens.HomeScreen> {
            Home(
                navController = navController,
                viewModel = viewModel,
                isThemeButtonClicked = isThemeButtonClicked
            )
        }
        composable<Screens.TransactionScreen> { Transaction(navController = navController, viewModel = viewModel) }
    }
}

