package com.ablanco.marvellab.core.di

import com.ablanco.marvellab.core.domain.repository.CharactersRepository

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-08.
 * MarvelLab.
 */
interface CoreComponent {

    val charactersRepository: CharactersRepository
}

interface CoreComponentOwner {

    val coreComponent: CoreComponent
}