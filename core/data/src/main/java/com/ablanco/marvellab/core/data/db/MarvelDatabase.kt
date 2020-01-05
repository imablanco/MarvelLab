package com.ablanco.marvellab.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ablanco.marvellab.core.data.db.dao.CharacterComicCrossRefDao
import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.db.model.CharacterComicCrossRef
import com.ablanco.marvellab.core.data.db.model.CharacterEntity
import com.ablanco.marvellab.core.data.db.model.ComicEntity

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Database(
    entities = [CharacterEntity::class, ComicEntity::class, CharacterComicCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun characterComicCrossRefDao(): CharacterComicCrossRefDao
}

fun CreateDatabase(context: Context) =
    Room.inMemoryDatabaseBuilder(
        context,
        MarvelDatabase::class.java
    ).build()
