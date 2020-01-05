package com.ablanco.marvellab.core.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
data class ComicWithCharacters(
    @Embedded
    val comic: ComicEntity,
    @Relation(
        parentColumn = "comicId",
        entityColumn = "characterId",
        associateBy = Junction(CharacterComicCrossRef::class)
    )
    val characters: List<CharacterEntity>
)