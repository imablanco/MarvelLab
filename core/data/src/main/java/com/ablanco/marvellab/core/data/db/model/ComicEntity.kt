package com.ablanco.marvellab.core.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Entity(tableName = "comics")
data class ComicEntity(
    @PrimaryKey val comicId:  String,
    val title: String?,
    val releaseDate: Date?,
    val coverImageUrl: String?
)