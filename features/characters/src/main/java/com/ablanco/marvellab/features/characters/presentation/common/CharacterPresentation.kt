package com.ablanco.marvellab.features.characters.presentation.common

import com.ablanco.marvellab.core.domain.model.Character

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
data class CharacterPresentation(val character: Character, val isFavorite: Boolean)