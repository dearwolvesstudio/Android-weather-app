package com.decenternet.core.dagger.modules

import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.ITimeService
import com.decenternet.core.services.TimeService
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class TimeServiceModule {

    @Provides
    @Singleton
    internal fun provideTimeService(stringService: IStringService): ITimeService {
        return TimeService(stringService)
    }

}
