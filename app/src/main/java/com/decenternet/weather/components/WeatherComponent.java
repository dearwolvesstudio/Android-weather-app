package com.decenternet.weather.components;

import com.decenternet.core.dagger.components.CoreComponent;
import com.decenternet.core.dagger.features.FeatureScope;
import com.decenternet.weather.WeatherApplication;
import com.decenternet.weather.ui.main.MainActivity;

import dagger.Component;

@FeatureScope
@Component(dependencies = {CoreComponent.class})
public interface WeatherComponent {
    void inject(WeatherApplication jiffyJabApplication);
    void inject(MainActivity mainActivity);
}
