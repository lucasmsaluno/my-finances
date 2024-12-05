package dev.lucasm.finn.data.repository

import androidx.room.Query
import dev.lucasm.finn.data.dao.TransactionsDao
import dev.lucasm.finn.data.model.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsDao: TransactionsDao
): TransactionsRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionsDao.getAllTransactions()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        return transactionsDao.insertTransaction(transaction)
    }

    override suspend fun removeTransaction(transaction: Transaction) {
        return transactionsDao.removeTransaction(transaction)
    }

    override suspend fun filterByIncomes(): Flow<List<Transaction>> {
        return transactionsDao.filterByIncomes()
    }

    override suspend fun filterByExpenses(): Flow<List<Transaction>> {
        return transactionsDao.filterByExpenses()
    }

    override suspend fun getTotalIncome(): Double {
        return transactionsDao.getTotalIncome()
    }

    override suspend fun getTotalExpense(): Double {
        return transactionsDao.getTotalExpense()
    }

    override suspend fun getTotalBalance(): Double {
        return transactionsDao.getTotalBalance()
    }
}