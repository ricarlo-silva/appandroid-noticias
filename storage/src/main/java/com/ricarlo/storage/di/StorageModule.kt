package com.ricarlo.storage.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object StorageModule {
    val modules = module {
        single<DataStore<Preferences>> {
            androidContext().createDataStore("data")
        }
    }
}
