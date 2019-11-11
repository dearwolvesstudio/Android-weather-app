package com.decenternet.weather.components

import com.decenternet.core.dagger.components.CoreComponent
import com.decenternet.core.dagger.features.FeatureScope
import com.decenternet.weather.WeatherApplication
import com.decenternet.weather.ui.main.MainActivity

import dagger.Component

@FeatureScope
@Component(dependencies = arrayOf(CoreComponent::class))
interface WeatherComponent {
    fun inject(jiffyJabApplication: WeatherApplication)
    fun inject(mainActivity: MainActivity)
}
