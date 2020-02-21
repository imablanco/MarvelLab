package com.ablanco.marvellab.core.data.api.service

import com.ablanco.marvellab.core.data.api.model.BaseResponseData
import com.ablanco.marvellab.core.data.api.model.CharacterData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
interface CharactersService {

    @GET("v1/public/characters")
    suspend fun searchCharacters(
        @Query("nameStartsWith") name: String?,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponseData<CharacterData>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: String
    ): BaseResponseData<CharacterData>

    @GET("/v1/public/comics/{comicId}/characters")
    suspend fun getComicCharacters(
        @Path("comicId") comicId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): BaseResponseData<CharacterData>
}