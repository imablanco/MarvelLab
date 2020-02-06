package com.ablanco.marvellab.shared.navigation

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */

sealed class Feature

object Welcome : Feature()
object Home : Feature()
object Profile : Feature()
class Characters(val characterId: String? = null) : Feature()
class Comics(val comicId: String? = null) : Feature()