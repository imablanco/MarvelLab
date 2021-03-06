package com.ablanco.marvellab.features.home.presentation

import com.ablanco.marvellab.core.domain.model.config.HomeSection
import com.ablanco.marvellab.core.domain.model.config.HomeSectionType
import com.ablanco.marvellab.shared.navigation.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */

fun HomeSection.toUi(): HomeSectionUi = HomeSectionUi(name, icon, type?.toFeature())

private fun HomeSectionType.toFeature(): Feature? = when (this) {
    HomeSectionType.Profile -> Profile
    HomeSectionType.Characters -> Characters()
    HomeSectionType.Comics -> Comics()
    HomeSectionType.Favorites -> Favorites
}