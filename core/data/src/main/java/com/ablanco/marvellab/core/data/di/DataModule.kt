package com.ablanco.marvellab.core.data.di

import com.ablanco.marvellab.core.data.repository.AuthRepositoryImpl
import com.ablanco.marvellab.core.data.repository.CharactersRepositoryImpl
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Module
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun providesCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository

    @Singleton
    @Binds
    abstract fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}