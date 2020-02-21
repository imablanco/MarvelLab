package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */

@Entity(tableName = "characters_comic")
data class CharacterComics(
    @PrimaryKey val characterId: String,
    val comicIds: List<String>
)