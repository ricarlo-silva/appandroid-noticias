package br.com.ricarlo.network

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

inline fun <reified T> T.toJson(): String? {
    return try {
        moshi.adapter(T::class.java).toJson(this)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> fromJson(json: String?): T? {
    if (json.isNullOrEmpty()) return null

    return moshi.adapter(T::class.java).fromJson(json)
}

fun String.readFileFromAssetsOrResources(context: Context? = null): String {
    return try {
        context?.assets?.readAssetsFile(this)
                ?: throw Resources.NotFoundException()
    } catch (ex: Exception) {
        this::javaClass::class.java.classLoader
                ?.getResource(this)?.readText()
                ?: throw Resources.NotFoundException()
    }
}

fun AssetManager.readAssetsFile(fileName: String): String = open(fileName)
        .bufferedReader().use { it.readText() }
