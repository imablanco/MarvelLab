package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.delay

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersApiDataSource {

    suspend fun getCharacters(): List<Character> {
        delay(1000)
        return emptyList()
    }
}