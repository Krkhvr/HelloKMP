package com.krkhvr.kotlinproject

import android.app.Application
import com.expensesApp.db.AppDatabase
import data.repository.DatabaseDriverFactory
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule(
                AppDatabase.invoke(DatabaseDriverFactory(this@MainApplication).createDriver())
            ))
        }
    }
}