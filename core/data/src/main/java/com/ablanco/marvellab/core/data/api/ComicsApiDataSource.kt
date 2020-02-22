package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.data.api.model.ComicData
import com.ablanco.marvellab.core.data.api.service.ComicsService
import com.ablanco.marvellab.core.data.api.service.buildListResource
import com.ablanco.marvellab.core.data.api.service.buildSingleResource
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.Resource
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class ComicsApiDataSource @Inject constructor(private val comicsService: ComicsService) {

    suspend fun searchComics(
        search: String? = null,
        offset: Int = 0
    ): Resource<List<Comic>> {
        return buildListResource {
            comicsService.searchComics(
                search,
                PAGE_SIZE,
                offset
            )
        }.map { comics -> comics.map(ComicData::toDomain) }
    }

    suspend fun getComic(characterId: String): Resource<Comic> {
        return buildSingleResource { comicsService.getComic(characterId) }
            .map(ComicData::toDomain)
    }

    suspend fun getCharacterComics(comicId: String, offset: Int = 0): Resource<List<Comic>> {
        return buildListResource {
            comicsService.getCharacterComics(
                comicId,
                PAGE_SIZE,
                offset
            )
        }.map { comics -> comics.map(ComicData::toDomain) }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}