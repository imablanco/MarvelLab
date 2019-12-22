package com.ablanco.marvellab.core.domain.model

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-22.
 * MarvelLab.
 */
data class Character(
    val id: String,
    val name: String?,
    val description: String?,
    val imageUrl: String
)