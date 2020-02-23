package com.ablanco.marvellab.core.data.api.model

import com.ablanco.marvellab.core.data.api.FireStoreEntity

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
data class FavoriteData(
    val userId: String,
    val favoriteId: String,
    val name: String?,
    val imageUrl: String?,
    val type: FavoriteTypeData,
    val createdAt: Long
) : FireStoreEntity {

    override val fireStoreId: String
        get() = "${userId}_${favoriteId}"
}