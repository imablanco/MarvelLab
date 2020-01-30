package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersApiDataSource @Inject constructor() {

    suspend fun searchCharacters(search: String? = null, offset: Int = 0): List<Character> {
        delay(250)
        val limit = offset + CHARACTERS_PAGE_SIZE
        return search?.let {
            SampleData.characters.filter { it.name?.contains(search, true) == true }
        } ?: SampleData.characters
    }

    suspend fun getComicCharacters(comicId: String, offset: Int = 0): List<Character> {
        delay(250)
        return SampleData.comicCharacters[comicId].orEmpty()
    }

    companion object {
        private const val CHARACTERS_PAGE_SIZE = 20
    }

}