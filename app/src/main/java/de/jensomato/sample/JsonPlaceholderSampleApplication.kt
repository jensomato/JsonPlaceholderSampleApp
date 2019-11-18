package de.jensomato.sample

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class JsonPlaceholderSampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@JsonPlaceholderSampleApplication)
            modules(appModule)
        }
    }

}