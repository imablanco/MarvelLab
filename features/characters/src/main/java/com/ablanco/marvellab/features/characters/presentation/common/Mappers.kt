package com.ablanco.marvellab.features.characters.presentation.common

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */

fun Character.toPresentation(isFavorite: Boolean = false) =
    CharacterPresentation(this, isFavorite)

fun Comic.toPresentation(isFavorite: Boolean = false) =
    ComicPresentation(this, isFavorite)

fun List<CharacterPresentation>.mapCharacterPresentationsWithFavorites(
    favorites: List<Favorite>
): List<CharacterPresentation> {
    val favoritesIds = favorites
        .filter { it.favoriteType == FavoriteType.Character }
        .map(Favorite::id)

    return map { it.copy(isFavorite = it.character.id in favoritesIds) }
}

fun List<ComicPresentation>.mapComicPresentationsWithFavorites(
    favorites: List<Favorite>
): List<ComicPresentation> {
    val favoritesIds = favorites
        .filter { it.favoriteType == FavoriteType.Comic }
        .map(Favorite::id)

    return map { it.copy(isFavorite = it.comic.id in favoritesIds) }
}

fun List<Character>.mapCharactersWithFavorites(
    favorites: List<Favorite>
): List<CharacterPresentation> {
    val favoritesIds = favorites
        .filter { it.favoriteType == FavoriteType.Character }
        .map(Favorite::id)

    return map { it.toPresentation(isFavorite = it.id in favoritesIds) }
}

fun List<Comic>.mapComicsWithFavorites(
    favorites: List<Favorite>
): List<ComicPresentation> {
    val favoritesIds = favorites
        .filter { it.favoriteType == FavoriteType.Comic }
        .map(Favorite::id)

    return map { it.toPresentation(isFavorite = it.id in favoritesIds) }
}