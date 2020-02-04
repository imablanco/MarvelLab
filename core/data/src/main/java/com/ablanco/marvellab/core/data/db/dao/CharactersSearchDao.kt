package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablanco.marvellab.core.data.db.model.CharacterSearchEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Dao
interface CharactersSearchDao {

    @Query("""select * from characters_search where search = :search""")
    fun getCharactersSearch(search: String? = null): Flow<CharacterSearchEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterSearchEntity: CharacterSearchEntity)
}