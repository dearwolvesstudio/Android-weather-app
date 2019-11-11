package com.decenternet.core.dagger.modules

import com.decenternet.core.interfaces.rest.IWeatherRestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class WeatherRestServiceModule {

    @Provides
    @Singleton
    internal fun provideWeatherRestService(retrofit: Retrofit): IWeatherRestService {
        return retrofit.create(IWeatherRestService::class.java)
    }
}