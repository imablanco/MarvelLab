package com.ablanco.marvellab.core.data.db

import com.ablanco.marvellab.core.data.db.dao.CharacterComicCrossRefDao
import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.db.model.CharacterComicCrossRef
import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersDbDataSource(
    private val charactersDao: CharactersDao,
    private val characterComicCrossRefDao: CharacterComicCrossRefDao
) {

    fun searchCharacters(search: String? = null): Flow<List<Character>> =
        charactersDao.searchCharacters(search).map { list -> list.map { it.toDomain() } }

    fun getComicCharacters(comicId: String): Flow<List<Character>> =
        charactersDao.getComicCharacters(comicId).map { entity ->
            entity.characters.map { it.toDomain() }
        }

    suspend fun saveCharacters(characters: List<Character>) =
        charactersDao.insertAll(characters.map { it.toEntity() })

    suspend fun saveComicCharacters(comicId: String, characters: List<Character>) =
        characterComicCrossRefDao.insertCrossRefs(
            characters.map { CharacterComicCrossRef(it.id, comicId) }
        )
}