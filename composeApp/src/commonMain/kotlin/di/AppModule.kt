package di

import data.ExpenseManager
import data.repository.ExpenseRepositoryImpl
import domain.repository.ExpenseRepository
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import presentation.screens.expense.ExpensesViewModel

fun appModule() = module {

    single { ExpenseManager }.withOptions { createdAtStart() }
    single<ExpenseRepository> { ExpenseRepositoryImpl(get()) }
    factory { ExpensesViewModel(get()) }

}