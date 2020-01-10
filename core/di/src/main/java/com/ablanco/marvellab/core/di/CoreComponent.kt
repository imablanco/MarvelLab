package com.ablanco.marvellab.core.di

import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.domain.repository.CharactersRepository

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
interface CoreComponent {

    val charactersRepository: CharactersRepository

    val authRepository: AuthRepository
}

interface CoreComponentOwner {

    val coreComponent: CoreComponent
}