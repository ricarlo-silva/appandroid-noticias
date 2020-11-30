package br.com.ricarlo.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

fun Any.toJson(prettyPrinting: Boolean = false): String? {
    return try {
        GsonBuilder().apply {
            if (prettyPrinting) {
                setPrettyPrinting()
            }
        }.create().toJson(this)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> fromJson(json: String?): T? {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}