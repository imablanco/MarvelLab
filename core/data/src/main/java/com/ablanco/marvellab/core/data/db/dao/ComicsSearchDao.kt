package com.ablanco.marvellab.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ablanco.marvellab.core.data.db.model.ComicSearchEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Dao
interface ComicsSearchDao {

    @Query("""select * from comics_search where search = :search""")
    fun getComicsSearch(search: String? = null): Flow<ComicSearchEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comicSearchEntity: ComicSearchEntity)
}