package di

import com.expensesApp.db.AppDatabase
import data.repository.ExpenseRepositoryImpl
import domain.repository.ExpenseRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import presentation.screens.expense.ExpensesViewModel

fun appModule(appDatabase: AppDatabase) = module {
    single<HttpClient> { HttpClient { install(ContentNegotiation) { json() } } }
    single<ExpenseRepository> { ExpenseRepositoryImpl(appDatabase, get()) }
    factory { ExpensesViewModel(get()) }
}