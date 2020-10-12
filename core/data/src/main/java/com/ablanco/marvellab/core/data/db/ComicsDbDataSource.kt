package com.ablanco.marvellab.core.data.db

import com.ablanco.marvellab.core.data.db.dao.CharacterComicsDao
import com.ablanco.marvellab.core.data.db.dao.ComicsDao
import com.ablanco.marvellab.core.data.db.dao.ComicsSearchDao
import com.ablanco.marvellab.core.data.db.model.CharacterComics
import com.ablanco.marvellab.core.data.db.model.ComicSearchEntity
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class ComicsDbDataSource @Inject constructor(
    private val comicsDao: ComicsDao,
    private val comicsSearchDao: ComicsSearchDao,
    private val characterComicsDao: CharacterComicsDao
) {

    fun searchComics(search: String? = null): Flow<List<Comic>> = flow {
        comicsSearchDao.getComicsSearch(search.toString()).collect { search ->
            search?.let {
                val comicsIds = search.comics
                val comics =
                    comicsDao.getComicsById(comicsIds).map { it.toDomain() }
                /*As SQL returns comics in random order, sort them according to search entity*/
                val orderById = comicsIds.withIndex().associate { it.value to it.index }
                emit(comics.sortedBy { orderById[it.id] })
            } ?: emit(emptyList<Comic>())
        }
    }

    fun getComic(comicId: String): Flow<Comic?> = comicsDao.getComic(comicId).map { it?.toDomain() }

    fun getCharacterComics(characterId: String): Flow<List<Comic>> = flow {
        characterComicsDao.getCharacterComics(characterId).collect { entity ->
            entity?.let {
                val comicsIds = entity.comicIds
                val comics =
                    comicsDao.getComicsById(comicsIds).map { it.toDomain() }
                /*As SQL returns comics in random order, sort them according to search entity*/
                val orderById = comicsIds.withIndex().associate { it.value to it.index }
                emit(comics.sortedBy { orderById[it.id] })
            } ?: emit(emptyList<Comic>())
        }
    }

    suspend fun saveComicsSearch(search: String?, comics: List<Comic>) =
        comicsSearchDao.insert(ComicSearchEntity(search.toString(), comics.map(Comic::id)))

    suspend fun saveComics(comics: List<Comic>) =
        comicsDao.insertAll(comics.map { it.toEntity() })

    suspend fun saveComic(comic: Comic) = comicsDao.insert(comic.toEntity())

    suspend fun saveCharacterComics(characterId: String, comics: List<Comic>) =
        characterComicsDao.insert(CharacterComics(characterId, comics.map(Comic::id)))
}
