package dev.lucasm.finn.data.repository

import androidx.room.Query
import dev.lucasm.finn.data.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun getAllTransactions (): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun removeTransaction(transaction: Transaction)

    fun getTotalIncome(): Double

    fun getTotalExpense(): Double

    fun getTotalBalance(): Double
}