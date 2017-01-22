package com.noticias_now.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ricarlo on 13/10/2016.
 */

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(IApsNoticias.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.connectTimeout(40, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);

        Retrofit retrofit = builder
                .client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }
}
