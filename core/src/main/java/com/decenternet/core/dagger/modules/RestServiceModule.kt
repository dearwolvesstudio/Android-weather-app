package com.decenternet.core.dagger.modules

import com.decenternet.core.BuildConfig
import com.decenternet.core.utilities.DateDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class RestServiceModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val apiKey = BuildConfig.API_KEY
        return OkHttpClient.Builder().addInterceptor {
            var request: Request = it.request()
            val url: HttpUrl = request.url().newBuilder().addQueryParameter("appid",apiKey).build()
            request = request.newBuilder().url(url).build();
            it.proceed(request)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val baseUri = BuildConfig.SERVER_URL
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(Date::class.java, DateDeserializer())
                        .create()
                )
            )
            .baseUrl(baseUri)
            .build()

        return retrofit
    }

}
