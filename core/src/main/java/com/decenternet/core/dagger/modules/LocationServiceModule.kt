package com.decenternet.core.dagger.modules

import android.content.Context
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.services.LocationService
import com.decenternet.core.services.PermissionsService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class LocationServiceModule {
    @Provides
    @Singleton
    internal fun providePermissionsService(context: Context, permissionsService: IPermissionsService): ILocationService {
        return LocationService(context, permissionsService)
    }
}
