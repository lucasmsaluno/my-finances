package dev.lucasm.finn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.lucasm.finn.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions")
    fun getAllTransactions (): Flow<List<Transaction>>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun removeTransaction(transaction: Transaction)

    @Query("SELECT SUM(price) FROM transactions WHERE type = 'income'")
    fun getTotalIncome(): Double

    @Query("SELECT SUM(price) FROM transactions WHERE type = 'expense'")
    fun getTotalExpense(): Double

    @Query("SELECT (SELECT SUM(price) FROM transactions WHERE type = 'income') " +
            "- " +
            "(SELECT SUM(price) FROM transactions WHERE type = 'expension')")
    fun getTotalBalance(): Double

}
