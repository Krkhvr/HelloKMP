import data.ExpenseManager
import data.repository.ExpenseRepositoryImpl
import domain.model.Expense
import domain.model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExpenseRepoTest {
    /*private val expenseManager = ExpenseManager
    private val repo = ExpenseRepositoryImpl(expenseManager)

    @Test
    fun expense_list_is_not_empty() {
        // GIVEN
        val expenseList = mutableListOf<Expense>()

        // WHEN
        expenseList.addAll(repo.getAllExpenses())

        // THEN
        assertTrue(expenseList.isNotEmpty())
    }

    @Test
    fun add_new_expense() {
        // GIVEN
        val expenseList = repo.getAllExpenses()

        // WHEN
        repo.addExpense(
            Expense(
                amount = 90.30,
                category = ExpenseCategory.PARTY,
                description = "Combustible"
            )
        )

        // THEN
        assertContains(expenseList, expenseList.find { it.id == 7L })
    }

    @Test
    fun edit_expense() {
        // GIVEN
        val expenseListBefore = repo.getAllExpenses()

        // WHEN
        val newExpenseId = 7L
        repo.addExpense(
            Expense(
                amount = 90.30,
                category = ExpenseCategory.PARTY,
                description = "Combustible"
            )
        )
        assertNotNull(expenseListBefore.find { it.id == newExpenseId })

        val updatedExpense = Expense(
            id = newExpenseId,
            amount = 33.45,
            category = ExpenseCategory.OTHER,
            description = "Ropa"
        )
        repo.editExpense(updatedExpense)

        // THEN
        val expenseListAfter = repo.getAllExpenses()
        assertEquals(updatedExpense, expenseListAfter.find { it.id == newExpenseId })
    }

    @Test
    fun get_all_categories() {
        // GIVEN
        val categoryList = mutableListOf<ExpenseCategory>()

        // WHEN
        categoryList.addAll(repo.getCategories())

        // THEN
        assertTrue(categoryList.isNotEmpty())
    }

    @Test
    fun check_all_categories(){
        // GIVEN
        val allCategories = listOf(
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY,
            ExpenseCategory.SNACKS,
            ExpenseCategory.COFFEE,
            ExpenseCategory.CAR,
            ExpenseCategory.HOUSE,
            ExpenseCategory.OTHER
        )

        // WHEN
        val repoCategories = repo.getCategories()

        // THEN
        assertEquals(allCategories, repoCategories)
    }*/
}