package com.ablanco.marvellab.features.comics.di.list

import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.features.comics.presentation.list.ComicsListViewModelFactory
import com.ablanco.marvellab.features.comics.ui.list.ComicsListFragment
import dagger.Component

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface ComicsListComponent {

    val comicsListViewModelFactory: ComicsListViewModelFactory

    fun inject(comicsListFragment: ComicsListFragment)
}