package com.ablanco.marvellab.features.comics.presentation.detail

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
sealed class ComicDetailViewAction : ViewAction

data class GoToCharacterDetailAction(val characterId: String) : ComicDetailViewAction()

