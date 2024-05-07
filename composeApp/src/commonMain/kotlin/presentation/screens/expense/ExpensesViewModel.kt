package presentation.screens.expense

import domain.model.Expense
import domain.model.ExpenseCategory
import domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    var total: Double = 0.0
)

class ExpensesViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState = _uiState.asStateFlow()

    private val allExpenses = expenseRepository.getAllExpenses()

    init {
        getAllExpenses()
    }

    private fun getAllExpenses(){
        viewModelScope.launch {
            updateState()
        }
    }

    private fun updateState(){
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(expenses = allExpenses, total = allExpenses.sumOf { it.amount })
            }
        }
    }

    fun addExpense(expense: Expense){
        viewModelScope.launch {
            expenseRepository.addExpense(expense)
            updateState()
        }
    }

    fun editExpense(expense: Expense){
        viewModelScope.launch {
            expenseRepository.editExpense(expense)
            updateState()
        }
    }

    /*fun deleteExpense(expense: Expense){
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
            updateState()
        }
    }*/

    fun getExpenseById(id: Long): Expense{
        return allExpenses.first{ it.id == id }
    }

    fun getCategories(): List<ExpenseCategory>{
        return expenseRepository.getCategories()
    }
}