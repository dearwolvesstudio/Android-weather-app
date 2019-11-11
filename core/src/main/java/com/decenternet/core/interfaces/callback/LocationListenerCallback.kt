package com.decenternet.core.interfaces.callback

import android.location.Location

interface LocationListenerCallback {
    fun onLocationFound(location: Location?)
    fun onLocationNotFound()
}