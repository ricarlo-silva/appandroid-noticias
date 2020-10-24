package br.com.ricarlo.common.firebase.remoteconfig

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

internal class FirebaseRemoteConfigManager(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : IFirebaseRemoteConfigManager {

    init {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnFailureListener {
                Log.e(javaClass.simpleName, it.message.orEmpty())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> fetchSync(key: String, clazz: Class<T>): T {
        return when (clazz) {
            String::class.java -> firebaseRemoteConfig.getString(key)
            Double::class.java -> firebaseRemoteConfig.getDouble(key)
            Boolean::class.java -> firebaseRemoteConfig.getBoolean(key)
            Long::class.java -> firebaseRemoteConfig.getLong(key)
            else -> {
                throw RuntimeException("${javaClass.simpleName} type no implemented")
            }
        } as T
    }

    override suspend fun <T> fetchAsync(key: String, clazz: Class<T>): T {
        return withContext(Dispatchers.IO) {
            try {
                firebaseRemoteConfig.fetchAndActivate().await()
                fetchSync(key = key, clazz = clazz)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is FirebaseRemoteConfigClientException -> {
                        // offline mode
                        fetchSync(key = key, clazz = clazz)
                    }
                    else -> throw throwable
                }
            }
        }
    }
}
