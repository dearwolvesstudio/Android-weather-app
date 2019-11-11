package com.decenternet.core.dagger.modules

import android.content.Context
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.services.StringService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class StringServiceModule {

    @Provides
    @Singleton
    internal fun provideStringService(context: Context): IStringService {
        return StringService(context)
    }

}
