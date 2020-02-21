package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.data.api.model.CharacterData
import com.ablanco.marvellab.core.data.api.service.CharactersService
import com.ablanco.marvellab.core.data.api.service.buildListResource
import com.ablanco.marvellab.core.data.api.service.buildSingleResource
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Resource
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersApiDataSource @Inject constructor(private val charactersService: CharactersService) {

    suspend fun searchCharacters(
        search: String? = null,
        offset: Int = 0
    ): Resource<List<Character>> {
        return buildListResource {
            charactersService.searchCharacters(
                search,
                PAGE_SIZE,
                offset
            )
        }.map { characters -> characters.map(CharacterData::toDomain) }
    }

    suspend fun getCharacter(characterId: String): Resource<Character> {
        return buildSingleResource { charactersService.getCharacter(characterId) }
            .map(CharacterData::toDomain)
    }

    suspend fun getComicCharacters(comicId: String, offset: Int = 0): Resource<List<Character>> {
        return buildListResource {
            charactersService.getComicCharacters(
                comicId,
                PAGE_SIZE,
                offset
            )
        }.map { characters -> characters.map(CharacterData::toDomain) }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}