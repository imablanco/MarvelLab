package com.ablanco.marvellab.core.data.di

import com.ablanco.marvellab.core.data.repository.*
import com.ablanco.marvellab.core.domain.repository.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository

    @Singleton
    @Binds
    abstract fun providesComicsRepository(impl: ComicsRepositoryImpl): ComicsRepository

    @Singleton
    @Binds
    abstract fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun providesUserRepository(impl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun providesConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository

}