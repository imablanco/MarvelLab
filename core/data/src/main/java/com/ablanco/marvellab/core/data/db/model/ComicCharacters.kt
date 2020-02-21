package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */

@Entity(tableName = "comic_characters")
data class ComicCharacters(
    @PrimaryKey val comicId: String,
    val charactersIds: List<String>
)