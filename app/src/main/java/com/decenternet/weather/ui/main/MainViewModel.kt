package com.decenternet.weather.ui.main

import androidx.lifecycle.ViewModel
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.weather.R

class MainViewModel(
    var stringService: IStringService,
    var permissionsService: IPermissionsService
) : ViewModel() {


    fun hasPermissionText():String {
        if(permissionsService.hasLocationAccess()) {
            return stringService.get(R.string.no_location_permission)
        }
        else {
            return stringService.get(R.string.has_location_permission)
        }
    }

}