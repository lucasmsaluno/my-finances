package dev.lucasm.finn.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.lucasm.finn.data.model.Transaction
import dev.lucasm.finn.data.repository.TransactionsRepository
import dev.lucasm.finn.utils.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TransactionsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsUiState())
    val state = _state.asStateFlow()

    init {
        getAllTrancactions()
    }

    fun onFilter (filter: Filter) {
        when (filter) {
            is Filter.All -> getAllTrancactions()
            Filter.Expenses -> getExpenses()
            Filter.Incomes -> getIncomes()
        }
    }

    fun getAllTrancactions () {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)

            repository.getAllTransactions().collect { listOfTransactions ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    transactions = listOfTransactions
                )

                updateSums(listOfTransactions)
            }
        }
    }

    private fun getIncomes () {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)

            repository.getAllTransactions().collect { listOfTransactions ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    transactions = listOfTransactions.filter { it.type == "income" }
                )
            }
        }
    }

    private fun getExpenses () {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)

            repository.getAllTransactions().collect { listOfTransactions ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    transactions = listOfTransactions.filter { it.type == "expense" }
                )
            }
        }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.removeTransaction(transaction)
        }
    }

    private fun updateSums(transactions: List<Transaction>) {
        val incomes = transactions.filter { it.type == "income" }.sumOf { it.price }
        val expenses = transactions.filter { it.type == "expense" }.sumOf { it.price }
        val total = incomes - expenses

        _state.value = _state.value.copy(
            incomes = incomes,
            expenses = expenses,
            total = total
        )
    }
}

