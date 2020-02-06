package com.ablanco.marvellab.core.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
data class CharacterWithComics(
    @Embedded
    val character: CharacterEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "comicId",
        associateBy = Junction(CharacterComicCrossRef::class)
    )
    val comics: List<ComicEntity>
)