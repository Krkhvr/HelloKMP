package domain.repository

import domain.model.Expense
import domain.model.ExpenseCategory

interface ExpenseRepository {
    fun getAllExpenses(): List<Expense>
    fun addExpense(expense: Expense)
    fun editExpense(expense: Expense)
    fun getCategories(): List<ExpenseCategory>
}