package com.ablanco.marvellab.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ablanco.marvellab.core.data.BuildConfig
import com.ablanco.marvellab.core.data.db.dao.*
import com.ablanco.marvellab.core.data.db.model.*
import java.lang.reflect.Method

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Database(
    entities = [
        CharacterEntity::class,
        CharacterSearchEntity::class,
        ComicEntity::class,
        ComicSearchEntity::class,
        CharacterComicCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverter::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
    abstract fun charactersSearchDao(): CharactersSearchDao
    abstract fun comicsDao(): ComicsDao
    abstract fun comicsSearchDao(): ComicsSearchDao
    abstract fun characterComicCrossRefDao(): CharacterComicCrossRefDao
}

fun CreateDatabase(context: Context) =
    Room.inMemoryDatabaseBuilder(
        context,
        MarvelDatabase::class.java
    ).build().debug()


private fun <T : RoomDatabase> T.debug(): T = apply {
    if (BuildConfig.DEBUG) {
        try {
            val debugDB = Class.forName("com.amitshekhar.DebugDB")
            val argTypes = arrayOf<Class<*>>(HashMap::class.java)
            val inMemoryDatabases: HashMap<String, SupportSQLiteDatabase?> = HashMap()
            // set your inMemory databases
            inMemoryDatabases["InMemoryOne.db"] = openHelper.writableDatabase
            val setRoomInMemoryDatabase: Method =
                debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
            setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
        } catch (ignore: Exception) {
        }
    }
}