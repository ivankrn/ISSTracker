package ru.ivankrn.isstracker

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import ru.ivankrn.isstracker.di.apiModule
import ru.ivankrn.isstracker.di.issTrackerAppModule

class ISSTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ISSTrackerApp)
            modules(apiModule, issTrackerAppModule)
        }
    }

}