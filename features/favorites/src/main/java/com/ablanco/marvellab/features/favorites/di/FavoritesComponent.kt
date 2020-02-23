package com.ablanco.marvellab.features.favorites.di

import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.features.favorites.presentation.FavoritesViewModelFactory
import com.ablanco.marvellab.features.favorites.ui.FavoritesFragment
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface FavoritesComponent {

    val favoritesViewModelFactory: FavoritesViewModelFactory

    fun inject(favoritesFragment: FavoritesFragment)
}