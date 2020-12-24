package com.ricarlo.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

suspend inline fun <reified T : Any> DataStore<Preferences>.save(key: String, value: T) {
    edit {
        it[preferencesKey<T>(key)] = value
    }
}

suspend inline fun <reified T : Any> DataStore<Preferences>.get(key: String, defaultValue: T? = null): T? {
    return data.map {
        it[preferencesKey<T>(key)] ?: defaultValue
    }.firstOrNull()
}

suspend inline fun <reified T : Any> DataStore<Preferences>.delete(key: String) {
    edit {
        it.remove(preferencesKey<T>(key))
    }
}

suspend fun DataStore<Preferences>.clear() {
    edit {
        it.clear()
    }
}

suspend inline fun <reified T : Any> DataStore<Preferences>.hasKey(key: String): Boolean {
    return data.map {
        it.contains(preferencesKey<T>(key))
    }.firstOrNull() ?: false
}
