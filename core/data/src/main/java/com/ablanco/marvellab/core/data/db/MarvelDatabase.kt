package com.ablanco.marvellab.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ablanco.marvellab.core.data.BuildConfig
import com.ablanco.marvellab.core.data.db.dao.CharacterComicCrossRefDao
import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.db.model.CharacterComicCrossRef
import com.ablanco.marvellab.core.data.db.model.CharacterEntity
import com.ablanco.marvellab.core.data.db.model.ComicEntity
import com.amitshekhar.DebugDB

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
    ).build().debug()


private fun <T : RoomDatabase> T.debug(): T = apply {
    if (BuildConfig.DEBUG) {
        DebugDB.setInMemoryRoomDatabases(hashMapOf("Room" to openHelper.writableDatabase))
        /* try {
             val debugDB = Class.forName("com.amitshekhar.DebugDB")
             val argTypes = arrayOf<Class<*>>(HashMap::class.java)
             val inMemoryDatabases: HashMap<String, SupportSQLiteDatabase?> = HashMap()
             // set your inMemory databases
             inMemoryDatabases["InMemoryOne.db"] = this.openHelper.writableDatabase
             val setRoomInMemoryDatabase: Method =
                 debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
             setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
         } catch (ignore: Exception) {
         }*/
    }
}