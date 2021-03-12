package com.ricarlo.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

suspend inline fun <reified T : Any> DataStore<Preferences>.save(key: String, value: T) {
    edit {
        it[getKey<T>(key)] = value
    }
}

inline fun <reified T : Any> getKey(name: String): Key<T> {
    val key = when (T::class) {
        Int::class -> {
            intPreferencesKey(name)
        }
        Double::class -> {
            doublePreferencesKey(name)
        }
        String::class -> {
            stringPreferencesKey(name)
        }
        Boolean::class -> {
            booleanPreferencesKey(name)
        }
        Float::class -> {
            floatPreferencesKey(name)
        }
        Long::class -> {
            longPreferencesKey(name)
        }
        else -> throw IllegalStateException("Unsupported type")
    }
    @Suppress("UNCHECKED_CAST")
    return key as Key<T>
}

suspend inline fun <reified T : Any> DataStore<Preferences>.get(
    key: String,
    defaultValue: T? = null
): T? {
    return data.map {
        it[getKey<T>(key)] ?: defaultValue
    }.firstOrNull()
}

suspend inline fun <reified T : Any> DataStore<Preferences>.delete(key: String) {
    edit {
        it.remove(getKey<T>(key))
    }
}

suspend fun DataStore<Preferences>.clear() {
    edit {
        it.clear()
    }
}

suspend inline fun <reified T : Any> DataStore<Preferences>.hasKey(key: String): Boolean {
    return data.map {
        it.contains(getKey<T>(key))
    }.firstOrNull() ?: false
}
