package presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.koin.core.parameter.parametersOf
import presentation.screens.expense.ExpensesScreen
import presentation.screens.expense.ExpensesViewModel
import presentation.screens.expense_details.ExpenseDetailScreen
import presentation.ui.theme.getColorsTheme

@Composable
fun Navigation(navigator: Navigator) {

    val colors = getColorsTheme()
    val viewModel = koinViewModel(ExpensesViewModel::class) { parametersOf() }

    NavHost(
        modifier = Modifier.background(colors.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(
                uiState = uiState,
                onExpenseClick = {
                    navigator.navigate("/addExpenses/${it.id}")
                },
                onDeleteExpense = {
                    viewModel.deleteExpense(it.id)
                }
            )
        }

        scene(route = "/addExpenses/{id}?") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            val expense = idFromPath?.let { id -> viewModel.getExpenseById(id) }

            ExpenseDetailScreen(expense = expense, categoryList = viewModel.getCategories()) {
                if (expense == null) {
                    viewModel.addExpense(it)
                } else {
                    viewModel.editExpense(it)
                }
                navigator.popBackStack()
            }
        }
    }
}