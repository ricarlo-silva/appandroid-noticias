package br.com.ricarlo.common.util.resources.di

import android.content.Context
import br.com.ricarlo.common.util.resources.IResourcesManager
import br.com.ricarlo.common.util.resources.ResourcesManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object ResourcesModule {
    val modules = module {

        factory<Context> { androidContext() }

        single<IResourcesManager> {
            ResourcesManagerImpl(get())
        }
    }
}