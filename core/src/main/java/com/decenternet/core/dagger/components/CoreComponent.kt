package com.decenternet.core.dagger.components

import android.content.Context
import com.decenternet.core.dagger.modules.*
import com.decenternet.core.interfaces.ILocationService
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(RestServiceModule::class, ContextModule::class, StringServiceModule::class, PermissionServiceModule::class, SharedPrefenceManagerModule::class, TimeServiceModule::class, LocationServiceModule::class))
@Singleton
interface CoreComponent {

    fun provideApplicationContext(): Context
    fun provideIStringService(): IStringService
    fun provideIPermissionsService(): IPermissionsService
    fun provideILocationService(): ILocationService

}
