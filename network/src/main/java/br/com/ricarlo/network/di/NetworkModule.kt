package br.com.ricarlo.network.di

import br.com.ricarlo.network.ServiceGenerator
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    val module = module {

        single<Retrofit> {
            ServiceGenerator.retrofit(get(), get(), get())
        }

        single<OkHttpClient> {
            ServiceGenerator.httpClient(get())
        }

        single<Converter.Factory> {
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        }
    }
}
