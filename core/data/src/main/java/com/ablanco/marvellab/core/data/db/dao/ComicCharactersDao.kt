package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablanco.marvellab.core.data.db.model.ComicCharacters
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
@Dao
interface ComicCharactersDao {


    @Query("select * from comic_characters where comicId = :comicId")
    fun getComicCharacters(comicId: String): Flow<ComicCharacters?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comicCharacters: ComicCharacters)

}