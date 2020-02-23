package com.ablanco.marvellab.core.domain.extensions

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */

fun Character.toFavorite(): Favorite = Favorite(id, name, imageUrl, FavoriteType.Character)

fun Comic.toFavorite(): Favorite = Favorite(id, title, coverImageUrl, FavoriteType.Comic)