package com.ablanco.marvellab.features.welcome.di

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.features.welcome.presentation.splash.SplashViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.splash.SplashActivity
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
@ActivityScope
@Component(dependencies = [CoreComponent::class])
interface SplashComponent {

    val splashViewModelFactory: SplashViewModelFactory

    fun inject(splashActivity: SplashActivity)
}
