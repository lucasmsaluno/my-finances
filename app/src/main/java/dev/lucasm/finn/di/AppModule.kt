package dev.lucasm.finn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.lucasm.finn.data.dao.TransactionsDao
import dev.lucasm.finn.data.database.TransactionsDatabase
import dev.lucasm.finn.data.repository.TransactionRepositoryImpl
import dev.lucasm.finn.data.repository.TransactionsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideDao (database: TransactionsDatabase) = database.transactionsDao()

    @Provides
    @Singleton
    fun provideDatabase (@ApplicationContext context: Context): TransactionsDatabase =
        Room.databaseBuilder (
            context,
            TransactionsDatabase::class.java,
            "transactions_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRepository (dao: TransactionsDao): TransactionsRepository = TransactionRepositoryImpl(dao)
}