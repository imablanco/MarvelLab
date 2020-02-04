package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.successOf
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersApiDataSource @Inject constructor() {

    suspend fun searchCharacters(
        search: String? = null,
        offset: Int = 0
    ): Resource<List<Character>> {
        delay(250)
        val characters = search?.let {
            SampleData.characters.filter { it.name?.contains(search, true) == true }
        } ?: SampleData.characters
        return successOf(characters.drop(offset).take(CHARACTERS_PAGE_SIZE))
    }

    suspend fun getCharacter(characterId: String): Resource<Character> {
        delay(250)
        val character = SampleData.characters.find { it.id == characterId }
        return character?.let { successOf(it) } ?: failOf(Throwable())
    }

    suspend fun getComicCharacters(comicId: String, offset: Int = 0): Resource<List<Character>> {
        delay(250)
        val limit = offset + CHARACTERS_PAGE_SIZE
        return successOf(SampleData.comicCharacters[comicId].orEmpty())
    }

    companion object {
        private const val CHARACTERS_PAGE_SIZE = 20
    }

}