package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.ConfigRepository
import com.ablanco.marvellab.core.domain.repository.UserRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Singleton
@Component(modules = [DataModule::class, DbModule::class])
interface DataComponent : CoreComponent {

    override val charactersRepository: CharactersRepository

    override val authRepository: AuthRepository

    override val userRepository: UserRepository

    override val configRepository: ConfigRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DataComponent
    }
}