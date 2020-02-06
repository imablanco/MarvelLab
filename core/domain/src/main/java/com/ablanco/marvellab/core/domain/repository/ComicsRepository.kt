package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-05.
 * MarvelLab.
 */
interface ComicsRepository {

    fun searchComics(search: String? = null, offset: Int = 0): Flow<Resource<List<Comic>>>

    fun getComic(comicId: String): Flow<Resource<Comic>>

    fun getCharacterComics(characterId: String, offset: Int = 0): Flow<Resource<List<Comic>>>
}