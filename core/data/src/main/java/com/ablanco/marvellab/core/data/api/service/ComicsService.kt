package com.ablanco.marvellab.core.data.api.service

import com.ablanco.marvellab.core.data.api.model.BaseResponseData
import com.ablanco.marvellab.core.data.api.model.ComicData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
interface ComicsService {

    @GET("/v1/public/comics")
    suspend fun searchComics(
        @Query("titleStartsWith") title: String?,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponseData<ComicData>

    @GET("v1/public/comics/{comicId}")
    suspend fun getComic(
        @Path("comicId") comicId: String
    ): BaseResponseData<ComicData>

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") characterId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponseData<ComicData>
}