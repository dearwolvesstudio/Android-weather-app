package com.decenternet.core.dagger.modules

import com.decenternet.core.BuildConfig
import com.decenternet.core.utilities.DateDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class RestServiceModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        httpClient: OkHttpClient): Retrofit {

        var baseUri = BuildConfig.SERVER_URL


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
