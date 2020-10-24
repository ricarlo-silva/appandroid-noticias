package br.com.ricarlo.common.firebase.remoteconfig

interface IFirebaseRemoteConfigManager {
    fun <T> fetchSync(@Feature key: String, clazz: Class<T>): T
    suspend fun <T> fetchAsync(@Feature key: String, clazz: Class<T>): T
}
