package br.com.ricarlo.common.inapp.update.di

import br.com.ricarlo.common.inapp.update.UpdateViewModel
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal object InAppUpdateModule {
    val module = module {

        single<AppUpdateManager> {
            AppUpdateManagerFactory.create(get())
        }

        single {
            UpdateViewModel(get(), get())
        }
    }
}