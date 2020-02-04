package com.ablanco.marvellab.core.data.api

import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
internal object SampleData {

    val characters = 0.rangeTo(100).map {
        Character(
            it.toString(),
            "Character $it",
            "This is the description for Character $it",
            null
        )
    }

    val comics = listOf(
        Comic("0", "Iron Man #1", Date(), "")
    )

    val comicCharacters = mapOf("0" to characters)
}