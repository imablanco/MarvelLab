package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.CharactersApiDataSource
import com.ablanco.marvellab.core.data.db.CharactersDbDataSource
import com.ablanco.marvellab.core.domain.model.Character
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

    override fun searchCharacters(search: String?, offset: Int): Flow<List<Character>> = flow {
        dbDataSource.searchCharacters(search).collect { dbCharacters ->
            if (dbCharacters.isEmpty()) {
                apiDataSource.searchCharacters(search, offset).also { apiCharacters ->
                    if (apiCharacters.isNotEmpty()) {
                        dbDataSource.saveCharacters(apiCharacters)
                    } else {
                        emit(emptyList())
                    }
                }
            } else {
                emit(dbCharacters)
            }
        }
    }

    override fun getComicCharacters(comicId: String, offset: Int): Flow<List<Character>> = flow {
        dbDataSource.getComicCharacters(comicId).collect { dbCharacters ->
            if (dbCharacters.isEmpty()) {
                apiDataSource.getComicCharacters(comicId).also { apiCharacters ->
                    if (apiCharacters.isNotEmpty()) {
                        dbDataSource.saveCharacters(apiCharacters)
                        dbDataSource.saveComicCharacters(comicId, apiCharacters)
                    } else {
                        emit(emptyList())
                    }
                }
            } else {
                emit(dbCharacters)
            }
        }
    }
}