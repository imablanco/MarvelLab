package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-22.
 * MarvelLab.
 */
interface CharactersRepository {

    fun searchCharacters(search: String? = null, offset: Int = 0): Flow<List<Character>>

    fun getComicCharacters(comicId: String, offset: Int = 0): Flow<List<Character>>
}