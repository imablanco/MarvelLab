package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablanco.marvellab.core.data.db.model.CharacterEntity
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
    fun getCharacter(characterId: String): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)
}