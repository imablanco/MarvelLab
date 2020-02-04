package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-04.
 * MarvelLab.
 */

@Entity(tableName = "characters_search")
data class CharacterSearchEntity(
    @PrimaryKey val search: String,
    val characters: List<String>
)