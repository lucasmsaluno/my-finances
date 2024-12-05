package dev.lucasm.finn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.lucasm.finn.data.dao.TransactionsDao
import dev.lucasm.finn.data.model.Transaction

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}