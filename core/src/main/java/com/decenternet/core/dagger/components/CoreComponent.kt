package com.decenternet.core.dagger.components

import com.decenternet.core.dagger.modules.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RestServiceModule::class, ContextModule::class, StringServiceModule::class, PermissionServiceModule::class, SharedPrefenceManagerModule::class, TimeServiceModule::class])
@Singleton
interface CoreComponent {


}
