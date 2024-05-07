package previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.ExpenseManager
import presentation.screens.expense.AllExpensesHeader
import presentation.screens.expense.ExpensesItem
import presentation.screens.expense.ExpensesScreen
import presentation.screens.expense.ExpensesTotalHeader
import presentation.screens.expense.ExpensesUiState

@Preview(showBackground = true)
@Composable
fun ExpensesTotalHeaderPreviews() {
    ExpensesTotalHeader(total = 100.8)
}

@Preview(showBackground = true)
@Composable
fun AllExpensesHeaderPreviews() {
    AllExpensesHeader()
}

@Preview(showBackground = false)
@Composable
fun ExpensesItemPreviews() {
    ExpensesItem(
        expense = ExpenseManager.fakeExpenseList[0],
        onExpenseClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun ExpensesScreenPreviews() {
    ExpensesScreen(
        uiState = ExpensesUiState(
            expenses = ExpenseManager.fakeExpenseList,
            total= 1053.42
        )
    ) {

    }
}