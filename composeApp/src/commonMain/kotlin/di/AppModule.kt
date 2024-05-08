package di

import com.expensesApp.db.AppDatabase
import data.repository.ExpenseRepositoryImpl
import domain.repository.ExpenseRepository
import org.koin.dsl.module
import presentation.screens.expense.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase) }
    factory { ExpensesViewModel(get()) }
}