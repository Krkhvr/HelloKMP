package data

import domain.model.Expense
import domain.model.ExpenseCategory

object ExpenseManager{

    private var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 70.0,
            category = ExpenseCategory.GROCERIES,
            description = "Weekly buy"
        ),
        Expense(
            id = currentId++,
            amount = 10.3,
            category = ExpenseCategory.SNACKS,
            description = "Hommies"
        ),
        Expense(
            id = currentId++,
            amount = 21000.0,
            category = ExpenseCategory.CAR,
            description = "Audi A1"
        ),
        Expense(
            id = currentId++,
            amount = 120.0,
            category = ExpenseCategory.PARTY,
            description = "Weekly party"
        ),
        Expense(
            id = currentId++,
            amount = 25.0,
            category = ExpenseCategory.HOUSE,
            description = "Cleaning"
        ),
        Expense(
            id = currentId++,
            amount = 128.0,
            category = ExpenseCategory.OTHER,
            description = "Services"
        )
    )

    fun addNewExpense(expense: Expense){
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun editExpense(expense: Expense){
        fakeExpenseList.indexOfFirst {
            it.id == expense.id
        }.let {
            if (it != -1 ){
                fakeExpenseList[it] = fakeExpenseList[it].copy(
                    amount = expense.amount,
                    category = expense.category,
                    description = expense.description
                )
            }
        }
    }

    fun getCagetories(): List<ExpenseCategory>{
        return listOf(
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY,
            ExpenseCategory.SNACKS,
            ExpenseCategory.COFFEE,
            ExpenseCategory.CAR,
            ExpenseCategory.HOUSE,
            ExpenseCategory.OTHER
        )
    }
}