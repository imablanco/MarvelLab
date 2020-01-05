package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.data.api.CharactersApiDataSource
import com.ablanco.marvellab.core.data.db.CharactersDbDataSource
import com.ablanco.marvellab.core.data.db.CreateDatabase
import com.ablanco.marvellab.core.data.db.MarvelDatabase
import com.ablanco.marvellab.core.data.db.dao.CharacterComicCrossRefDao
import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.repository.CharactersRepositoryImpl
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-05.
 * MarvelLab.
 */
@Module
class DataModule {

    @Singleton
    @Provides
    fun providesDatabase(context: Context): MarvelDatabase = CreateDatabase(context)

    @Provides
    fun providesCharactersDao(db: MarvelDatabase): CharactersDao = db.charactersDao()

    @Provides
    fun providesCharacterComicCrossRefDao(db: MarvelDatabase): CharacterComicCrossRefDao =
        db.characterComicCrossRefDao()

    @Singleton
    @Provides
    fun providesCharactersRepository(
        api: CharactersApiDataSource,
        db: CharactersDbDataSource
    ): CharactersRepository = CharactersRepositoryImpl(api, db)

}