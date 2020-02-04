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

    @Query("""select * from characters where characterId in (:ids) """)
    suspend fun getCharactersById(ids: List<String>): List<CharacterEntity>

    @Query("""select * from characters where characterId = :characterId """)
    suspend fun getCharacter(characterId: String): CharacterEntity?

    @Transaction
    @Query("""select * from comics where comics.comicId = :comicId""")
    fun getComicCharacters(comicId: String): Flow<ComicWithCharacters>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)
}