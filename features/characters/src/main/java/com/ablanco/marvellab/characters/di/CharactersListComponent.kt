package com.ablanco.marvellab.characters.di

import com.ablanco.marvellab.characters.presentation.CharactersListViewModelFactory
import com.ablanco.marvellab.characters.ui.CharactersListFragment
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */
@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface CharactersListComponent {

    val charactersListViewModelFactory: CharactersListViewModelFactory

    fun inject(charactersListFragment: CharactersListFragment)
}