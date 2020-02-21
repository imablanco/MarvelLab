package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablanco.marvellab.core.data.db.model.CharacterComics
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
@Dao
interface CharacterComicsDao {

    @Query("select * from characters_comic where characterId = :characterId")
    fun getCharacterComics(characterId: String): Flow<CharacterComics?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterComics: CharacterComics)

}