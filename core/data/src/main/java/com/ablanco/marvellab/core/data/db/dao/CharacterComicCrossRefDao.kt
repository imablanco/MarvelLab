package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ablanco.marvellab.core.data.db.model.CharacterComicCrossRef

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Dao
interface CharacterComicCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<CharacterComicCrossRef>)
}