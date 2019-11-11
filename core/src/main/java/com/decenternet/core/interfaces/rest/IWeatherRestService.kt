package com.decenternet.core.interfaces.rest

import com.decenternet.core.models.dto.WeatherLocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherRestService {
    @GET("weather")
    fun getWeatherInformation(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double) : Call<WeatherLocationResponse>
}