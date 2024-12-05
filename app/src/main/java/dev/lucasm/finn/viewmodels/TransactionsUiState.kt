package dev.lucasm.finn.viewmodels

import dev.lucasm.finn.data.model.Transaction

data class TransactionsUiState (
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val incomes: Double = 0.0,
    val expenses: Double = 0.0,
    val total: Double = 0.0
)