package com.alan.projetopadrao

import android.app.Application
import com.alan.projetopadrao.data.api.RetrofitInit
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.koin.core.context.startKoin

class App : Application() {

    var listofModules = module{
        single { RetrofitInit() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(baseContext)
            modules(listofModules)
        }
    }
}