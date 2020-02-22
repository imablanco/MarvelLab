package com.ablanco.marvellab.core.domain.model.favorites

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
data class Favorite(
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val favoriteType: FavoriteType
)