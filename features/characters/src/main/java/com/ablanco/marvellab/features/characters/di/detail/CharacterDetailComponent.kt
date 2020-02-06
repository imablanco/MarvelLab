package com.ablanco.marvellab.features.characters.di.detail

import com.ablanco.marvellab.features.characters.presentation.detail.CharacterDetailViewModelFactory
import com.ablanco.marvellab.features.characters.ui.detail.CharacterDetailFragment
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.di.FragmentScope
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */

@Qualifier
annotation class CharacterId

@FragmentScope
@Component(dependencies = [CoreComponent::class])
interface CharacterDetailComponent {

    val characterDetailViewModelFactory: CharacterDetailViewModelFactory

    fun inject(characterDetailFragment: CharacterDetailFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun characterId(@CharacterId characterId: String): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): CharacterDetailComponent
    }
}