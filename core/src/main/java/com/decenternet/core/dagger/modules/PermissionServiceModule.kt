package com.decenternet.core.dagger.modules

import android.content.Context
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.services.PermissionsService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class PermissionServiceModule {
    @Provides
    @Singleton
    internal fun providePermissionsService(context: Context, stringService: IStringService): IPermissionsService {
        return PermissionsService(context, stringService)
    }
}
