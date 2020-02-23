package com.ablanco.marvellab.features.favorites.presentation

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */
sealed class FavoritesViewAction : ViewAction

class GoToCharacterDetailAction(val characterId : String) : FavoritesViewAction()

class GoToComicDetailViewAction(val comicId : String) : FavoritesViewAction()