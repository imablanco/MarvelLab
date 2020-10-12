package com.ablanco.marvellab.core.data.db

import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.db.dao.CharactersSearchDao
import com.ablanco.marvellab.core.data.db.dao.ComicCharactersDao
import com.ablanco.marvellab.core.data.db.model.CharacterSearchEntity
import com.ablanco.marvellab.core.data.db.model.ComicCharacters
import com.ablanco.marvellab.core.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersDbDataSource @Inject constructor(
    private val charactersDao: CharactersDao,
    private val charactersSearchDao: CharactersSearchDao,
    private val comicCharactersDao: ComicCharactersDao
) {

    fun searchCharacters(search: String? = null): Flow<List<Character>> = flow {
        charactersSearchDao.getCharactersSearch(search.toString()).collect { search ->
            search?.let {
                val characterIds = search.characters
                val characters =
                    charactersDao.getCharactersById(characterIds).map { it.toDomain() }
                /*As SQL returns characters in random order, sort them according to search entity*/
                val orderById = characterIds.withIndex().associate { it.value to it.index }
                emit(characters.sortedBy { orderById[it.id] })
            } ?: emit(emptyList<Character>())
        }
    }

    fun getCharacter(characterId: String): Flow<Character?> =
        charactersDao.getCharacter(characterId).map { it?.toDomain() }

    fun getComicCharacters(comicId: String): Flow<List<Character>> = flow {
        comicCharactersDao.getComicCharacters(comicId).collect { entity ->
            entity?.let {
                val characterIds = entity.charactersIds
                val characters =
                    charactersDao.getCharactersById(characterIds).map { it.toDomain() }
                /*As SQL returns characters in random order, sort them according to search entity*/
                val orderById = characterIds.withIndex().associate { it.value to it.index }
                emit(characters.sortedBy { orderById[it.id] })
            } ?: emit(emptyList<Character>())
        }
    }


    suspend fun saveCharactersSearch(search: String?, characters: List<Character>) =
        charactersSearchDao.insert(
            CharacterSearchEntity(
                search.toString(),
                characters.map(Character::id)
            )
        )

    suspend fun saveCharacters(characters: List<Character>) =
        charactersDao.insertAll(characters.map { it.toEntity() })

    suspend fun saveCharacter(character: Character) = charactersDao.insert(character.toEntity())

    suspend fun saveComicCharacters(comicId: String, characters: List<Character>) =
        comicCharactersDao.insert(ComicCharacters(comicId, characters.map(Character::id)))
}
