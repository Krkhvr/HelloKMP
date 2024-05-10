import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilExactlyOneExists
import domain.model.Expense
import domain.model.ExpenseCategory
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.navigation.Navigation
import presentation.screens.expense.ExpensesScreen
import presentation.screens.expense.ExpensesUiState
import presentation.screens.expense_details.ExpenseDetailScreen
import utils.EXPENSE_DETAIL_SCREEN_SUCCESS_TEST_TAG
import utils.EXPENSE_SCREEN_EMPTY_LIST_TEST_TAG
import utils.EXPENSE_SCREEN_ERROR_TEST_TAG
import utils.EXPENSE_SCREEN_ERROR_TEXT_TEST_TAG
import utils.EXPENSE_SCREEN_LOADING_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG
import utils.EXPENSE_SCREEN_SUCCESS_TEST_TAG
import utils.EXPENSE_SCREEN_TOTAL_TEST_TAG
import kotlin.test.Test

class ExpenseScreenUiTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenLoadingTest() = runComposeUiTest {
        val loadingUiState = ExpensesUiState.Loading
        setContent {
            ExpensesScreen(
                uiState = loadingUiState,
                onExpenseClick = {},
                onDeleteExpense = {}
            )
        }
        onNodeWithTag(EXPENSE_SCREEN_LOADING_TEST_TAG).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenErrorTest() = runComposeUiTest {
        val errorUiState = ExpensesUiState.Error(message = "Error de carga")
        val errorMessage = "Error: ${errorUiState.message}"

        setContent {
            ExpensesScreen(
                uiState = errorUiState,
                onExpenseClick = {},
                onDeleteExpense = {}
            )
        }
        onNodeWithTag(EXPENSE_SCREEN_ERROR_TEST_TAG).assertExists()
        onNodeWithTag(EXPENSE_SCREEN_ERROR_TEXT_TEST_TAG).assertTextEquals(errorMessage)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenSuccessTest() = runComposeUiTest {
        val expenses = listOf(
            Expense(
                id = 1,
                amount = 70.0,
                category = ExpenseCategory.GROCERIES,
                description = "Weekly buy"
            ),
            Expense(
                id = 2,
                amount = 10.3,
                category = ExpenseCategory.SNACKS,
                description = "Hommies"
            ),
            Expense(
                id = 3,
                amount = 21000.0,
                category = ExpenseCategory.CAR,
                description = "Audi A1"
            ),
            Expense(
                id = 4,
                amount = 5.2,
                category = ExpenseCategory.COFFEE,
                description = "Cappuccino"
            ),
            Expense(
                id = 5,
                amount = 120.0,
                category = ExpenseCategory.PARTY,
                description = "Weekly party"
            ),
            Expense(
                id = 6,
                amount = 25.0,
                category = ExpenseCategory.HOUSE,
                description = "Cleaning"
            ),
            Expense(
                id = 7,
                amount = 128.0,
                category = ExpenseCategory.OTHER,
                description = "Services"
            )
        )
        val successUiState = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })

        setContent {
            ExpensesScreen(
                uiState = successUiState,
                onExpenseClick = {},
                onDeleteExpense = {}
            )
        }
        onNodeWithTag(EXPENSE_SCREEN_SUCCESS_TEST_TAG).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenEmptyListTest() = runComposeUiTest {

        val successUiState = ExpensesUiState.Success(emptyList(), 0.0)

        setContent {
            ExpensesScreen(
                uiState = successUiState,
                onExpenseClick = {},
                onDeleteExpense = {}
            )
        }
        onNodeWithTag(EXPENSE_SCREEN_EMPTY_LIST_TEST_TAG).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenTotalTest() = runComposeUiTest {
        val expenses = listOf(
            Expense(
                id = 1,
                amount = 70.0,
                category = ExpenseCategory.GROCERIES,
                description = "Weekly buy"
            ),
            Expense(
                id = 2,
                amount = 10.3,
                category = ExpenseCategory.SNACKS,
                description = "Hommies"
            ),
            Expense(
                id = 3,
                amount = 21000.0,
                category = ExpenseCategory.CAR,
                description = "Audi A1"
            ),
            Expense(
                id = 4,
                amount = 5.2,
                category = ExpenseCategory.COFFEE,
                description = "Cappuccino"
            ),
            Expense(
                id = 5,
                amount = 120.0,
                category = ExpenseCategory.PARTY,
                description = "Weekly party"
            ),
            Expense(
                id = 6,
                amount = 25.0,
                category = ExpenseCategory.HOUSE,
                description = "Cleaning"
            ),
            Expense(
                id = 7,
                amount = 128.0,
                category = ExpenseCategory.OTHER,
                description = "Services"
            )
        )
        val successUiState = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })

        setContent {
            ExpensesScreen(
                uiState = successUiState,
                onExpenseClick = {},
                onDeleteExpense = {}
            )
        }
        onNodeWithTag(EXPENSE_SCREEN_SUCCESS_TEST_TAG).assertExists()
        onNodeWithTag(EXPENSE_SCREEN_TOTAL_TEST_TAG).assertTextEquals("${expenses.sumOf { it.amount }}")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun expenseScreenSuccessClickItemTest() = runComposeUiTest {
        val expenses = listOf(
            Expense(
                id = 1,
                amount = 70.0,
                category = ExpenseCategory.GROCERIES,
                description = "Weekly buy"
            ),
            Expense(
                id = 2,
                amount = 10.3,
                category = ExpenseCategory.SNACKS,
                description = "Hommies"
            ),
            Expense(
                id = 3,
                amount = 21000.0,
                category = ExpenseCategory.CAR,
                description = "Audi A1"
            ),
            Expense(
                id = 4,
                amount = 5.2,
                category = ExpenseCategory.COFFEE,
                description = "Cappuccino"
            ),
            Expense(
                id = 5,
                amount = 120.0,
                category = ExpenseCategory.PARTY,
                description = "Weekly party"
            ),
            Expense(
                id = 6,
                amount = 25.0,
                category = ExpenseCategory.HOUSE,
                description = "Cleaning"
            ),
            Expense(
                id = 7,
                amount = 128.0,
                category = ExpenseCategory.OTHER,
                description = "Services"
            )
        )
        val successUiState = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })

        setContent {
            PreComposeApp {
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    initialRoute = "/home"
                ) {
                    scene(route = "/home") {
                        ExpensesScreen(
                            uiState = successUiState,
                            onExpenseClick = {
                                navigator.navigate("/addExpenses/${it.id}")
                            },
                            onDeleteExpense = {}
                        )
                    }

                    scene(route = "/addExpenses/{id}?") {
                        ExpenseDetailScreen(
                            expense = successUiState.expenses[0],
                            categoryList = emptyList()
                        ) {}
                    }
                }
            }
        }
        onNodeWithTag("${EXPENSE_SCREEN_SUCCESS_CLICK_ITEM_TEST_TAG}_1").performClick()
        waitUntilExactlyOneExists(hasTestTag(EXPENSE_DETAIL_SCREEN_SUCCESS_TEST_TAG))
        onNodeWithTag(EXPENSE_DETAIL_SCREEN_SUCCESS_TEST_TAG).assertExists()
    }
}