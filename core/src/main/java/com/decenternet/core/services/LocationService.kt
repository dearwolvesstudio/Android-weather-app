package com.decenternet.core.services

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.callback.LocationListenerCallback
import com.decenternet.core.models.Method
import com.decenternet.core.utilities.Connectivity


class LocationService(private val context: Context, private val permissionsService: IPermissionsService) : ILocationService, LocationListener {

    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var method: Method? = null
    private var callback: LocationListenerCallback? = null

    override fun getLocation(method: Method, callback: LocationListenerCallback) {
        this.method = method
        this.callback = callback

        if(!permissionsService.hasLocationAccess()) {
            return
        }
        when (this.method) {
            Method.NETWORK, Method.NETWORK_THEN_GPS -> {
                val networkLocation =
                    this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (networkLocation != null) {
                    Log.d(
                        LOG_TAG,
                        "Last known location found for network provider : $networkLocation"
                    )
                    this.callback!!.onLocationFound(networkLocation)
                } else {
                    Log.d(LOG_TAG, "Request updates from network provider.")
                    this.requestUpdates(LocationManager.NETWORK_PROVIDER)
                }
            }
            Method.GPS -> {
                val gpsLocation =
                    this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (gpsLocation != null) {
                    Log.d(LOG_TAG, "Last known location found for GPS provider : $gpsLocation")
                    this.callback!!.onLocationFound(gpsLocation)
                } else {
                    Log.d(LOG_TAG, "Request updates from GPS provider.")
                    this.requestUpdates(LocationManager.GPS_PROVIDER)
                }
            }
        }
    }

    private fun requestUpdates(provider: String) {
        if(!permissionsService.hasLocationAccess()) {
            return
        }

        if (this.locationManager.isProviderEnabled(provider)) {
            if (provider.contentEquals(LocationManager.NETWORK_PROVIDER) && Connectivity.isConnected(
                    this.context
                )
            ) {
                Log.d(LOG_TAG, "Network connected, start listening : $provider")
                this.locationManager.requestLocationUpdates(
                    provider,
                    TIME_INTERVAL.toLong(),
                    DISTANCE_INTERVAL.toFloat(),
                    this
                )
            } else if (provider.contentEquals(LocationManager.GPS_PROVIDER) && Connectivity.isConnectedMobile(
                    this.context
                )
            ) {
                Log.d(LOG_TAG, "Mobile network connected, start listening : $provider")
                this.locationManager.requestLocationUpdates(
                    provider,
                    TIME_INTERVAL.toLong(),
                    DISTANCE_INTERVAL.toFloat(),
                    this
                )
            } else {
                Log.d(LOG_TAG, "Proper network not connected for provider : $provider")
                this.onProviderDisabled(provider)
            }
        } else {
            this.onProviderDisabled(provider)
        }
    }

    override fun cancel() {
        Log.d(LOG_TAG, "Locating canceled.")
        this.locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        Log.d(
            LOG_TAG,
            "Location found : " + location.latitude + ", " + location.longitude + if (location.hasAccuracy()) " : +- " + location.accuracy + " meters" else ""
        )
        this.locationManager.removeUpdates(this)
        this.callback!!.onLocationFound(location)
    }

    override fun onProviderDisabled(provider: String) {
        Log.d(LOG_TAG, "Provider disabled : $provider")
        if (this.method == Method.NETWORK_THEN_GPS && provider.contentEquals(LocationManager.NETWORK_PROVIDER)) {
            // Network provider disabled, try GPS
            Log.d(LOG_TAG, "Requesst updates from GPS provider, network provider disabled.")
            this.requestUpdates(LocationManager.GPS_PROVIDER)
        } else {
            this.locationManager.removeUpdates(this)
            this.callback!!.onLocationNotFound()
        }
    }

    override fun onProviderEnabled(provider: String) {
        Log.d(LOG_TAG, "Provider enabled : $provider")
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        Log.d(LOG_TAG, "Provided status changed : $provider : status : $status")
    }

    companion object {

        private val LOG_TAG = "locator"

        private val TIME_INTERVAL = 100 // minimum time between updates in milliseconds
        private val DISTANCE_INTERVAL = 1 // minimum distance between updates in meters
    }

}