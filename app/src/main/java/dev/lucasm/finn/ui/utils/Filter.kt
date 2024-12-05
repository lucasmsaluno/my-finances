package dev.lucasm.finn.ui.utils

sealed class Filter {
    data object All: Filter()
    data object Incomes: Filter()
    data object Expenses: Filter ()
}