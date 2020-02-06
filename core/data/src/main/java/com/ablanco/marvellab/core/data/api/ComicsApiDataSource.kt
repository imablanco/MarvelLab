package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.successOf
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class ComicsApiDataSource @Inject constructor() {

    suspend fun searchComics(
        search: String? = null,
        offset: Int = 0
    ): Resource<List<Comic>> {
        delay(250)
        val comics = search?.let {
            SampleData.comics.filter { it.title?.contains(search, true) == true }
        } ?: SampleData.comics
        return successOf(comics.drop(offset).take(PAGE_SIZE))
    }

    suspend fun getComic(characterId: String): Resource<Comic> {
        delay(250)
        val character = SampleData.comics.find { it.id == characterId }
        return character?.let { successOf(it) } ?: failOf(Throwable())
    }

    suspend fun getComicCharacters(comicId: String, offset: Int = 0): Resource<List<Comic>> {
        delay(250)
        return successOf(SampleData.characterComics[comicId].orEmpty().drop(offset).take(PAGE_SIZE))
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}