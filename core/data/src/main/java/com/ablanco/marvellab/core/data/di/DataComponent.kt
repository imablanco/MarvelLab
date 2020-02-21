package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.di.CoreComponent
import com.ablanco.marvellab.core.domain.repository.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Singleton
@Component(modules = [RepositoryModule::class, DbModule::class, ApiModule::class])
interface DataComponent : CoreComponent {

    override val charactersRepository: CharactersRepository

    override val comicsRepository: ComicsRepository

    override val authRepository: AuthRepository

    override val userRepository: UserRepository

    override val configRepository: ConfigRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun apiBaseUrl(@ApiBaseUrl apiBaseUrl: String): Builder

        @BindsInstance
        fun apiKey(@ApiKey apiKey: String): Builder

        fun build(): DataComponent
    }
}