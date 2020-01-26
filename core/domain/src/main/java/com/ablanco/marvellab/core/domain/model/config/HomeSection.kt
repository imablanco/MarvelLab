package com.ablanco.marvellab.core.domain.model.config

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
data class HomeSection(
    val name: String?,
    val icon: String?,
    val type: HomeSectionType?,
    val order: Int?
)