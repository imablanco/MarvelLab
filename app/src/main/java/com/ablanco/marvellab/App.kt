package com.ablanco.marvellab

import android.app.Application
import com.ablanco.marvellab.core.data.di.DaggerDataComponent
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.CoreComponentOwner

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-08.
 * MarvelLab.
 */
class App : Application(), CoreComponentOwner {

    override val coreComponent: CoreComponent
        get() = DaggerDataComponent.builder().context(this).build()
}