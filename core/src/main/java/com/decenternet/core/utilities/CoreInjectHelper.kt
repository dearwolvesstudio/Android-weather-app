package com.decenternet.core.utilities

import android.content.Context
import com.decenternet.core.dagger.components.CoreComponent
import com.decenternet.core.interfaces.CoreComponentProvider


object CoreInjectHelper {
    fun providesCoreComponent(context: Context): CoreComponent? {
        return if (context is CoreComponentProvider) {
            (context as CoreComponentProvider).providesCoreComponent()
        } else null

    }
}
