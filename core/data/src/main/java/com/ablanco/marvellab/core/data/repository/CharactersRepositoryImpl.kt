package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.CharactersApiDataSource
import com.ablanco.marvellab.core.data.db.CharactersDbDataSource
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.successOf
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersRepositoryImpl @Inject constructor(
    private val apiDataSource: CharactersApiDataSource,
    private val dbDataSource: CharactersDbDataSource
) : CharactersRepository {

    override fun searchCharacters(search: String?, offset: Int): Flow<Resource<List<Character>>> =
        flow {
            dbDataSource.searchCharacters(search).collect { dbCharacters ->
                if (dbCharacters.size <= offset) {
                    apiDataSource.searchCharacters(search, offset).also { apiResource ->
                        apiResource.getOrNull()?.let { characters ->
                            if (characters.isNotEmpty()) {
                                val allCharacters = dbCharacters + characters
                                dbDataSource.saveCharacters(allCharacters)
                                dbDataSource.saveCharactersSearch(search, allCharacters)
                            } else {
                                emit(successOf(dbCharacters))
                            }
                        } ?: emit(apiResource)
                    }
                } else {
                    emit(successOf(dbCharacters))
                }
            }
        }

    override fun getCharacter(characterId: String): Flow<Resource<Character>> = flow {
        dbDataSource.getCharacter(characterId).collect { character ->
            if (character == null) {
                apiDataSource.getCharacter(characterId).fold(
                    { dbDataSource.saveCharacter(it) },
                    { emit(failOf(it)) }
                )
            } else {
                emit(successOf(character))
            }
        }
    }

    override fun getComicCharacters(comicId: String, offset: Int): Flow<Resource<List<Character>>> =
        flow {
            dbDataSource.getComicCharacters(comicId).collect { dbCharacters ->
                if (dbCharacters.size <= offset) {
                    apiDataSource.getComicCharacters(comicId, offset).also { apiResource ->
                        apiResource.getOrNull()?.let { characters ->
                            if (characters.isNotEmpty()) {
                                val allCharacters = dbCharacters + characters
                                dbDataSource.saveCharacters(allCharacters)
                                dbDataSource.saveComicCharacters(comicId, allCharacters)
                            } else {
                                emit(successOf(dbCharacters))
                            }
                        } ?: emit(apiResource)
                    }
                } else {
                    emit(successOf(dbCharacters))
                }
            }
        }
}