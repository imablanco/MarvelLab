package com.ablanco.marvellab.features.characters.di.list

import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.features.characters.presentation.list.CharactersListViewModelFactory
import com.ablanco.marvellab.features.characters.ui.list.CharactersListFragment
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