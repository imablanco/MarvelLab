package com.ablanco.marvellab.features.comics.presentation.common

import com.ablanco.marvellab.core.domain.model.Comic

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
data class ComicPresentation(val comic: Comic, val isFavorite: Boolean)