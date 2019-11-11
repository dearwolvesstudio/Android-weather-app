package com.decenternet.weather.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.decenternet.core.BuildConfig
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.callback.RequestPermissionCallback
import com.decenternet.core.interfaces.rest.IWeatherRestService
import com.decenternet.core.models.dto.WeatherLocationResponse
import com.decenternet.weather.R
import com.decenternet.weather.WeatherApplication
import com.decenternet.weather.databinding.ActivityMainBinding
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RequestPermissionCallback {

    lateinit var viewModel:MainViewModel

    @Inject
    lateinit var stringService:IStringService

    @Inject
    lateinit var permissionsService: IPermissionsService

    @Inject
    lateinit var locationService: ILocationService

    @Inject
    lateinit var weatherRestService: IWeatherRestService


    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as WeatherApplication).weatherComponent?.inject(this)
        viewModel = MainViewModel(stringService, permissionsService, locationService, weatherRestService)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.data.observe(this, Observer<WeatherLocationResponse> {
            val tempCelsius =  (it.main!!.temp?.minus(273.15))
            binding.currentTextView.setText(it.name!!)
            binding.tempTextView.setText(String.format(Locale.getDefault(), "%.0f°C", tempCelsius))
            binding.descriptionTextView.setText(it.weather!!.get(0).description)
            binding.humidityTextView.setText(String.format(Locale.getDefault(), "%d%%", it.main!!.humidity));
            binding.windTextView.setText(String.format(Locale.getDefault(), stringService.get(R.string.wind_unit_label), it.wind!!.speed))
            binding.weatherIcon.setImageURI(String.format(Locale.getDefault(), BuildConfig.WEATHER_IMAGE_URL_FORMAT, it.weather!!.get(0).icon))
        })

        viewModel.initialize()
    }

    fun askForLocation(view: View) {
        permissionsService.requestLocationAccess(this)
    }

    override fun onPermissionSuccess(permissionName: String) {
        when (permissionName) {
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                viewModel.getLocationAndUpdateWeatherDetails()
            }
        }
    }

    override fun onPermissionDeclined(permissionName: String) { }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsService.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
