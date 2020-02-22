package com.ablanco.marvellab.core.di

import com.ablanco.marvellab.core.domain.repository.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
interface CoreComponent {

    val charactersRepository: CharactersRepository

    val comicsRepository : ComicsRepository

    val authRepository: AuthRepository

    val userRepository: UserRepository

    val configRepository: ConfigRepository

    val favoritesRepository: FavoritesRepository
}

interface CoreComponentOwner {

    val coreComponent: CoreComponent
}