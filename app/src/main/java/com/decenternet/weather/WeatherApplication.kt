package com.decenternet.weather

import android.app.Application

import com.decenternet.core.dagger.components.CoreComponent
import com.decenternet.core.dagger.modules.ContextModule
import com.decenternet.core.interfaces.CoreComponentProvider
import com.decenternet.weather.components.WeatherComponent

class WeatherApplication : Application(), CoreComponentProvider {

    val weatherComponent: WeatherComponent?
        get() {
            if (_component == null) {
                _component = DaggerWeatherComponent
                    .builder()
                    .coreComponent(providesCoreComponent())
                    .build()
            }

            return _component
        }

    override fun providesCoreComponent(): CoreComponent {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent
                .builder()
                .contextModule(ContextModule(applicationContext))
                .build()
        }

        return coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        weatherComponent!!.inject(this)

    }

    companion object {

        private var coreComponent: CoreComponent? = null
        private var _component: WeatherComponent? = null
    }
}
