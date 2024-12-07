package dev.lucasm.finn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.lucasm.finn.navigation.AppNavigation
import dev.lucasm.finn.ui.theme.FinnTheme
import dev.lucasm.finn.viewmodels.TransactionsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }

            FinnTheme (darkTheme = isDarkTheme) {
                val viewModel = viewModel<TransactionsViewModel>()
                AppNavigation(
                    viewModel = viewModel,
                    isThemeButtonClicked = {
                        isDarkTheme = !isDarkTheme
                    }
                )
            }
        }
    }
}