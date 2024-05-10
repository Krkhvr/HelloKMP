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

sealed class ExpensesUiState{
    data object Loading: ExpensesUiState()
    data class Success(val expenses: List<Expense>, val total: Double): ExpensesUiState()
    data class Error(val message: String): ExpensesUiState()
}

class ExpensesViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getExpensesList()
    }

    private fun getExpensesList(){
        viewModelScope.launch {
            try{
                val expenses = expenseRepository.getAllExpenses()
                _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private suspend fun updateExpenseList(){
        try{
            val expenses = expenseRepository.getAllExpenses()
            _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
        }catch (e: Exception){
            _uiState.value = ExpensesUiState.Error(e.message ?: "Error desconocido")
        }
    }

    fun addExpense(expense: Expense){
        viewModelScope.launch {
            try {
                expenseRepository.addExpense(expense)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun editExpense(expense: Expense){
        viewModelScope.launch {
            try {
                expenseRepository.editExpense(expense)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun deleteExpense(id: Long){
        viewModelScope.launch {
            try {
                expenseRepository.deleteExpense(id)
                updateExpenseList()
            }catch (e: Exception){
                _uiState.value = ExpensesUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun getExpenseById(id: Long): Expense?{
        return (_uiState.value as? ExpensesUiState.Success)?.expenses?.firstOrNull{ it.id == id }
    }

    fun getCategories(): List<ExpenseCategory>{
        return expenseRepository.getCategories()
    }
}