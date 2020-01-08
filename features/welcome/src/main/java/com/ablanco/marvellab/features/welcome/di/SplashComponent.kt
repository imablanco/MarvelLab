package com.ablanco.marvellab.features.welcome.di

import com.ablanco.marvellab.core.data.di.DataComponent
import com.ablanco.marvellab.features.welcome.presentation.SplashViewModelFactory
import dagger.Component
import javax.inject.Scope

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
@SplashScope
@Component(dependencies = [DataComponent::class])
interface SplashComponent {

    val splashViewModelFactory: SplashViewModelFactory
}

@Scope
annotation class SplashScope