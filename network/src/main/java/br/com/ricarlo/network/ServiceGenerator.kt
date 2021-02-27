package br.com.ricarlo.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Created by ricarlo on 13/10/2016.
 */
object ServiceGenerator {

    fun httpClient(networkConfig: INetworkConfig): OkHttpClient {
        return OkHttpClient.Builder()
                .apply {
                    if (networkConfig.isDebug()) {
                        addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    }
                }
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val response = chain.proceed(chain.request())
                        if (response.isSuccessful.not()) {
                            // TODO
                        }
                        return response
                    }
                })
                .connectTimeout(networkConfig.connectTimeout(), TimeUnit.SECONDS)
                .readTimeout(networkConfig.readTimeout(), TimeUnit.SECONDS)
                .build()
    }

    fun retrofit(
            networkConfig: INetworkConfig,
            httpClient: OkHttpClient,
            converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(networkConfig.baseUrl())
                .addConverterFactory(converter)
                .client(httpClient)
                .build()
    }
}

class Service(private val retrofit: Retrofit) {
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
