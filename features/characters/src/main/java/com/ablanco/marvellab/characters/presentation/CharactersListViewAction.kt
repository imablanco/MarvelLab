package com.ablanco.marvellab.characters.presentation

import com.ablanco.marvellab.core.presentation.ViewAction

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
sealed class CharactersListViewAction : ViewAction

data class GoToCharacterDetail(val characterId: String) : CharactersListViewAction()