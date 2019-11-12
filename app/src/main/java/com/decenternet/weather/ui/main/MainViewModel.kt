package com.decenternet.weather.ui.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.callback.LocationListenerCallback
import com.decenternet.core.interfaces.rest.IWeatherRestService
import com.decenternet.core.models.Method
import com.decenternet.core.models.dto.WeatherLocationResponse
import com.decenternet.weather.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(var stringService: IStringService, var permissionsService: IPermissionsService, var locationService: ILocationService, var weatherRestService: IWeatherRestService) : ViewModel() {

    var hasLocationDetails:MutableLiveData<Boolean> = MutableLiveData()
    var isLoading:MutableLiveData<Boolean> = MutableLiveData()
    var isError:MutableLiveData<Boolean> = MutableLiveData()
    var infoText:MutableLiveData<String> = MutableLiveData()

    var data:MutableLiveData<WeatherLocationResponse> = MutableLiveData()

    fun initialize() {
        isError.value = false
        isLoading.value = false
        hasLocationDetails.value = permissionsService.hasLocationAccess()
        if(!permissionsService.hasLocationAccess()) {
            infoText.value = stringService.get(R.string.no_location_permission)
            isError.value = true
        }
        else {
            getLocationAndUpdateWeatherDetails()
        }
    }

    fun getLocationAndUpdateWeatherDetails() {
        hasLocationDetails.value = permissionsService.hasLocationAccess()
        isError.value = false
        isLoading.value = true
        infoText.value = stringService.get(R.string.getting_your_location)


        val callback = object: LocationListenerCallback {
            override fun onLocationFound(location: Location?) {
                locationService.cancel()
                (location)?.let {
                    getWeatherDetails(it)
                }
            }

            override fun onLocationNotFound() {
                locationService.cancel()
                infoText.value = stringService.get(R.string.location_not_found)
                isLoading.value = false
                isError.value = true
            }
        }

        locationService.getLocation(Method.NETWORK_THEN_GPS, callback)
    }


    private fun getWeatherDetails(location: Location) {

        infoText.value = stringService.get(R.string.getting_weather_information)
        isLoading.value = true

        val call: Call<WeatherLocationResponse> = weatherRestService.getWeatherInformation(location.latitude, location.longitude)
        call.enqueue(object: Callback<WeatherLocationResponse> {
            override fun onFailure(call: Call<WeatherLocationResponse>, t: Throwable) {
                infoText.value = t.localizedMessage
                isLoading.value = false
                isError.value = true
            }

            override fun onResponse(call: Call<WeatherLocationResponse>, response: Response<WeatherLocationResponse>) {
                isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    isError.value = false
                    data.value = response.body()!!
                }
                else {
                    isError.value = true
                    infoText.value = stringService.get(R.string.weather_fetching_error)
                }
            }
        })
    }



}