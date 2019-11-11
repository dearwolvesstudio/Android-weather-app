package com.decenternet.core.interfaces

import com.decenternet.core.dagger.components.CoreComponent

interface CoreComponentProvider {

    fun providesCoreComponent(): CoreComponent?
}
