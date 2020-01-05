package com.ablanco.marvellab.core.domain.model

import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
data class Comic(
    val id: String,
    val title: String?,
    val releaseDate: Date?,
    val coverImageUrl: String?
)