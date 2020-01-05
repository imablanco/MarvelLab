package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {

    val charactersRepository: CharactersRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DataComponent
    }
}