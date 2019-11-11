package com.decenternet.weather.ui.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.callback.LocationListenerCallback
import com.decenternet.core.models.Method
import com.decenternet.weather.R

class MainViewModel(var stringService: IStringService, var permissionsService: IPermissionsService, var locationService: ILocationService) : ViewModel() {

    var hasLocationDetails:MutableLiveData<Boolean> = MutableLiveData()
    var isLoading:MutableLiveData<Boolean> = MutableLiveData()
    var infoText:MutableLiveData<String> = MutableLiveData()

    fun initialize() {
        isLoading.value = false
        hasLocationDetails.value = permissionsService.hasLocationAccess()
        if(!permissionsService.hasLocationAccess()) {
            infoText.value = stringService.get(R.string.no_location_permission)
        }
        else {
            getLocationAndUpdateWeatherDetails()
        }
    }

    fun getLocationAndUpdateWeatherDetails() {
        hasLocationDetails.value = permissionsService.hasLocationAccess()

        isLoading.value = true
        infoText.value = stringService.get(R.string.getting_your_location)


        val callback = object: LocationListenerCallback {
            override fun onLocationFound(location: Location?) {
                locationService.cancel()
                infoText.value = "Location Found"
            }

            override fun onLocationNotFound() {
                locationService.cancel()
                infoText.value = "Location Not Found"
            }
        }

        locationService.getLocation(Method.NETWORK_THEN_GPS, callback)
    }



}