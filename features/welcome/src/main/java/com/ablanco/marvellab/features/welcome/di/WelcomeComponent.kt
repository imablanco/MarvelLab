package com.ablanco.marvellab.features.welcome.di

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.features.welcome.presentation.welcome.WelcomeViewModelFactory
import com.ablanco.marvellab.features.welcome.ui.welcome.WelcomeActivity
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
@ActivityScope
@Component(dependencies = [CoreComponent::class])
interface WelcomeComponent {

    val welcomeViewModelFactory: WelcomeViewModelFactory

    fun inject(welcomeActivity: WelcomeActivity)
}
