package com.noticias_now.app

import android.app.Application
import br.com.ricarlo.common.di.CommonModule
import com.noticias_now.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by ricarlo on 10/11/2016.
 */
class ApsNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            // Android context
            androidContext(this@ApsNewsApp)
            // modules
            modules(CommonModule.modules + AppModule.modules)
        }
    }

}