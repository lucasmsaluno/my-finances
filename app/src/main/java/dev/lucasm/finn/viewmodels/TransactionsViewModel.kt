package dev.lucasm.finn.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasm.finn.data.model.Transaction
import dev.lucasm.finn.data.repository.TransactionsRepository
import dev.lucasm.finn.ui.utils.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TransactionsRepository
): ViewModel() {
    private val _state = MutableStateFlow(TransactionsUiState())
    val state = _state.asStateFlow()

    fun filterTransactions(filter: Filter): Job{
        return when (filter) {
            is Filter.All -> {
                viewModelScope.launch (Dispatchers.IO) {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )

                    repository.getAllTransactions().collect { listOfTransactions ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            transactions = listOfTransactions
                        )
                    }
                }
            }

            Filter.Expenses -> {
                viewModelScope.launch (Dispatchers.IO) {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )

                    repository.filterByExpenses().collect { listOfExpenses ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            transactions = listOfExpenses
                        )
                    }
                }
            }
            Filter.Incomes -> {
                viewModelScope.launch (Dispatchers.IO) {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )

                    repository.filterByExpenses().collect { listOfExpenses ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            transactions = listOfExpenses
                        )
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch (Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = true
            )

            repository.getAllTransactions().collect { listOfTransactions ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    transactions = listOfTransactions
                )
            }

            updateTotals()
        }
    }

    private fun updateTotals () {
        viewModelScope.launch {
            val totalIncomes = repository.getTotalIncome()
            val totalExpenses = repository.getTotalExpense()
            val totalBalance = repository.getTotalBalance()

            _state.value = _state.value.copy(
                incomes = totalIncomes,
                expenses = totalExpenses,
                total = totalBalance
            )
        }
    }

    fun insertTransaction (transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)
            updateTotals()
        }
    }

    fun removeTransaction (transaction: Transaction) {
        viewModelScope.launch {
            repository.removeTransaction(transaction)
            updateTotals()
        }
    }
}