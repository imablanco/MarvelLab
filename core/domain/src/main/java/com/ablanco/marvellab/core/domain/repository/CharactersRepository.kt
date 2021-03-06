package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-22.
 * MarvelLab.
 */
interface CharactersRepository {

    fun searchCharacters(search: String? = null, offset: Int = 0): Flow<Resource<List<Character>>>

    fun getCharacter(characterId: String): Flow<Resource<Character>>

    fun getComicCharacters(comicId: String, offset: Int = 0): Flow<Resource<List<Character>>>
}