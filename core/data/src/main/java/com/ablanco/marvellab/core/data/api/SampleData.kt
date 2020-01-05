package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
internal object SampleData {

    val characters = listOf(
        Character("0", "Iron Man", "", ""),
        Character("1", "Captain America", "", ""),
        Character("2", "Thor", "", ""),
        Character("3", "Hulk", "", ""),
        Character("4", "Black Widow", "", ""),
        Character("5", "Thanos", "", "")
    )

    val comics = listOf(
        Comic("0", "Iron Man #1", Date(), "")
    )

    val comicCharacters = mapOf("0" to characters)
}