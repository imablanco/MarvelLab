package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.Index

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Entity(
    tableName = "character_comic_union",
    primaryKeys = ["characterId", "comicId"],
    indices = [Index("comicId")]
)
data class CharacterComicCrossRef(
    val characterId: String,
    val comicId: String
)