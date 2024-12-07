package dev.lucasm.finn.utils

sealed class Filter {
    data object All: Filter()
    data object Incomes: Filter ()
    data object Expenses: Filter ()
}