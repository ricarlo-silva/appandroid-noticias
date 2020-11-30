package br.com.ricarlo.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ricarlo on 13/10/2016.
 */
object ServiceGenerator {

    private val httpClient = OkHttpClient.Builder()

    private fun retrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
    }

    @JvmStatic
    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        httpClient.connectTimeout(40, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        return retrofit(baseUrl).create(serviceClass)
    }
}