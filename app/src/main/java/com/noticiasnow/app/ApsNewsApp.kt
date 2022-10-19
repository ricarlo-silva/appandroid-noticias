package com.noticiasnow.app

import android.app.Application
import br.com.ricarlo.common.di.CommonModule
import br.com.ricarlo.network.di.NetworkModule
import com.noticiasnow.di.AppModule
import com.ricarlo.storage.di.StorageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin

/**
 * Created by ricarlo on 10/11/2016.
 */
class ApsNewsApp : Application() {

    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    @KoinApiExtension
    private fun startDI() {
        startKoin {
            // Android context
            androidContext(this@ApsNewsApp)
            // modules
            modules(
                CommonModule.modules +
                    AppModule.modules +
                    NetworkModule.module +
                    StorageModule.modules
            )
        }
    }
}
