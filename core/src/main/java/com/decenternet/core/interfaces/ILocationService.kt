package com.decenternet.core.interfaces

import com.decenternet.core.interfaces.callback.LocationListenerCallback
import com.decenternet.core.models.Method


interface ILocationService {
    fun getLocation(method: Method, callback: LocationListenerCallback)
    fun cancel()
}