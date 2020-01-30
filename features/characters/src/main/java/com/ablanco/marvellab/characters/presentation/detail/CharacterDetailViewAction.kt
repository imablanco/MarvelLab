package com.ablanco.marvellab.characters.presentation.detail

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */
sealed class CharacterDetailViewAction : ViewAction

data class GoToCharacterComicAction(val comicId: String) : CharacterDetailViewAction()