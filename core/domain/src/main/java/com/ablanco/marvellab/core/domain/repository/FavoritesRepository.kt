package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
interface FavoritesRepository {

    fun getAllFavorites(): Flow<Resource<List<Favorite>>>

    suspend fun addFavorite(favorite: Favorite): Resource<Boolean>

    suspend fun removeFavorite(favorite: Favorite): Resource<Boolean>
}