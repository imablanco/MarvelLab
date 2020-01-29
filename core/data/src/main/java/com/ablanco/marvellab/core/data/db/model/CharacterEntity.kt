package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val characterId: String,
    val name: String?,
    val description: String?,
    val imageUrl: String?
)