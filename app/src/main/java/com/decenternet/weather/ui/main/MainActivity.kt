package com.decenternet.weather.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.weather.R
import com.decenternet.weather.WeatherApplication
import com.decenternet.weather.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var viewModel:MainViewModel

    @Inject
    lateinit var stringService:IStringService

    @Inject
    lateinit var permissionsService: IPermissionsService


    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as WeatherApplication).weatherComponent?.inject(this)
        viewModel = MainViewModel(stringService, permissionsService)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    fun askForLocation(view: View) {
        permissionsService.requestLocationAccess(this)
    }
}
