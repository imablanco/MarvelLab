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

    val comics = 0.rangeTo(100).map {
        Comic(
            it.toString(),
            "Comic #$it",
            Date(),
            null
        )
    }

    val comicCharacters =
        0.rangeTo(100).map { it.toString() }.associateWith { characters.shuffled() }

    val characterComics =
        0.rangeTo(100).map { it.toString() }.associateWith { comics.shuffled() }
}