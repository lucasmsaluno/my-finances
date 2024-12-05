package dev.lucasm.finn.data.repository

import androidx.room.Query
import dev.lucasm.finn.data.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    fun getAllTransactions (): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun removeTransaction(transaction: Transaction)

    suspend fun filterByIncomes(): Flow<List<Transaction>>

    suspend fun filterByExpenses(): Flow<List<Transaction>>

    suspend fun getTotalIncome(): Double

    suspend fun getTotalExpense(): Double

    suspend fun getTotalBalance(): Double
}