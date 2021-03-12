package br.com.ricarlo.network

interface INetworkConfig {
    fun baseUrl(): String
    fun connectTimeout(): Long
    fun readTimeout(): Long
    fun isDebug(): Boolean
}
