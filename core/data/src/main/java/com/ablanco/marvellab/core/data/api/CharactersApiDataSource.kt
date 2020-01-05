package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersApiDataSource @Inject constructor() {

    suspend fun searchCharacters(search: String? = null): List<Character> {
        delay(250)
        return search?.let {
            SampleData.characters.filter { it.name?.contains(search, true) == true }
        } ?: SampleData.characters
    }

    suspend fun getComicCharacters(comicId: String): List<Character> {
        delay(250)
        return SampleData.comicCharacters[comicId].orEmpty()
    }

}