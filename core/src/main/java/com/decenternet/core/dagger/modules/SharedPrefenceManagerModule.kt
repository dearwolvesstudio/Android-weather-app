package com.decenternet.core.dagger.modules

import android.content.Context
import com.decenternet.core.interfaces.ISharedPreferenceManager

import com.decenternet.core.services.SharedPreferenceService

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class SharedPrefenceManagerModule {
    @Provides
    @Singleton
    internal fun providePreferenceManager(context: Context): ISharedPreferenceManager {
        return SharedPreferenceService(context)
    }
}
