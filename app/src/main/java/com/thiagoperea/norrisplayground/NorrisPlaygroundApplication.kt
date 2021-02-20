package com.thiagoperea.norrisplayground

import android.app.Application
import com.thiagoperea.norrisplayground.business.businessModule
import com.thiagoperea.norrisplayground.datasource.datasourceModule
import com.thiagoperea.norrisplayground.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NorrisPlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NorrisPlaygroundApplication)
            modules(presentationModule, businessModule, datasourceModule)
        }
    }
}