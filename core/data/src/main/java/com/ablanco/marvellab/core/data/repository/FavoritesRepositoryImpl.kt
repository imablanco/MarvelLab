package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.FavoritesApiDataSource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesApiDataSource: FavoritesApiDataSource
) : FavoritesRepository {

    @ExperimentalCoroutinesApi
    override fun getAllFavorites(): Flow<Resource<List<Favorite>>> =
        favoritesApiDataSource.getFavorites()

    override suspend fun addFavorite(favorite: Favorite): Resource<Boolean> =
        favoritesApiDataSource.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: Favorite): Resource<Boolean> =
        favoritesApiDataSource.removeFavorite(favorite)
}