package com.ablanco.marvellab.core.data.db.dao

import androidx.room.*
import com.ablanco.marvellab.core.data.db.model.CharacterWithComics
import com.ablanco.marvellab.core.data.db.model.ComicEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Dao
interface ComicsDao {

    @Query("""select * from comics where comicId in (:ids) """)
    suspend fun getComicsById(ids: List<String>): List<ComicEntity>

    @Query("""select * from comics where comicId = :comicId """)
    suspend fun getComic(comicId: String): ComicEntity?

    @Transaction
    @Query("""select * from characters where characterId = :characterId""")
    fun getCharacterComics(characterId: String): Flow<CharacterWithComics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comics: List<ComicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comic: ComicEntity)
}