package com.ablanco.marvellab.features.home.di

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.features.home.presentation.HomeViewModelFactory
import com.ablanco.marvellab.features.home.ui.HomeActivity
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
@ActivityScope
@Component(dependencies = [CoreComponent::class])
interface HomeComponent {

    val homeViewModelFactory: HomeViewModelFactory

    fun inject(homeActivity: HomeActivity)
}
