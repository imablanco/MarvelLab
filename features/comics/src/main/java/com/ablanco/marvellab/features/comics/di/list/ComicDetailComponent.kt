package com.ablanco.marvellab.features.comics.di.list

import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.features.comics.presentation.detail.ComicDetailViewModelFactory
import com.ablanco.marvellab.features.comics.ui.detail.ComicDetailFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface ComicDetailComponent {

    val comicDetailViewModelFactory: ComicDetailViewModelFactory

    fun inject(comicDetailFragment: ComicDetailFragment)

    @Component.Builder
    interface Builder {

        fun coreComponent(coreComponent: CoreComponent): Builder

        @BindsInstance
        fun comicId(@ComicId comicId: String): Builder

        fun build(): ComicDetailComponent
    }
}

@Qualifier
annotation class ComicId