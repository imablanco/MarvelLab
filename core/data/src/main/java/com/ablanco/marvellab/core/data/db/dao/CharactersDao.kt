package com.ablanco.marvellab.core.data.db.dao

import androidx.room.*
import com.ablanco.marvellab.core.data.db.model.CharacterEntity
import com.ablanco.marvellab.core.data.db.model.ComicWithCharacters
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Dao
interface CharactersDao {

    @Query("""select * from characters where name like :search""")
    fun searchCharacters(search: String? = null): Flow<List<CharacterEntity>>

    @Transaction
    @Query("""select * from comics where comics.comicId = :comicId""")
    fun getComicCharacters(comicId: String): Flow<ComicWithCharacters>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)
}