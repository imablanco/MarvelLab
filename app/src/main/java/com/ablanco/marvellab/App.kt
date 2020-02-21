package com.ablanco.marvellab

import android.app.Application
import com.ablanco.marvellab.core.data.di.DaggerDataComponent
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.CoreComponentOwner
import com.ablanco.marvellab.shared.navigation.FeatureNavigator
import com.ablanco.marvellab.shared.navigation.FeatureNavigatorOwner

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-08.
 * MarvelLab.
 */
class App : Application(), CoreComponentOwner, FeatureNavigatorOwner {

    override val featureNavigator: FeatureNavigator = FeatureNavigatorImpl(this)

    private val _coreComponent: CoreComponent by lazy {
        DaggerDataComponent
            .builder()
            .context(this)
            .apiBaseUrl(Constants.ApiBaseUrl)
            .apiKey(Constants.ApiKey)
            .build()
    }
    override val coreComponent: CoreComponent
        get() = _coreComponent
}