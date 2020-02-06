package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.ComicsApiDataSource
import com.ablanco.marvellab.core.data.db.ComicsDbDataSource
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.successOf
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class ComicsRepositoryImpl @Inject constructor(
    private val apiDataSource: ComicsApiDataSource,
    private val dbDataSource: ComicsDbDataSource
) : ComicsRepository {

    override fun searchComics(search: String?, offset: Int): Flow<Resource<List<Comic>>> =
        flow {
            dbDataSource.searchComics(search).collect { dbComics ->
                if (dbComics.size <= offset) {
                    apiDataSource.searchComics(search, offset).also { apiResource ->
                        apiResource.getOrNull()?.let { comics ->
                            if (comics.isNotEmpty()) {
                                dbDataSource.saveComicsSearch(search, comics)
                            } else {
                                emit(successOf(dbComics))
                            }
                        } ?: emit(apiResource)
                    }
                } else {
                    emit(successOf(dbComics))
                }
            }
        }

    override fun getComic(comicId: String): Flow<Resource<Comic>> = flow {
        dbDataSource.getComic(comicId).collect { comic ->
            if (comic == null) {
                apiDataSource.getComic(comicId).fold(
                    { dbDataSource.saveComic(it) },
                    { emit(failOf(it)) }
                )
            } else {
                emit(successOf(comic))
            }
        }
    }

    override fun getCharacterComics(characterId: String, offset: Int): Flow<Resource<List<Comic>>> =
        flow {
            dbDataSource.getCharacterComics(characterId).collect { dbComics ->
                if (dbComics.isEmpty()) {
                    apiDataSource.getComicCharacters(characterId).also { apiResource ->
                        apiResource.getOrNull()?.let { comics ->
                            if (comics.isNotEmpty()) {
                                dbDataSource.saveComics(comics)
                                dbDataSource.saveCharacterComics(characterId, comics)
                            } else {
                                emit(successOf(emptyList()))
                            }
                        } ?: emit(apiResource)
                    }
                } else {
                    emit(successOf(dbComics))
                }
            }
        }

}