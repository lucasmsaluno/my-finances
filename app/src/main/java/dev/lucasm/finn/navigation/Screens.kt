package dev.lucasm.finn.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object HomeScreen: Screens()

    @Serializable
    data object TransactionScreen: Screens()
}