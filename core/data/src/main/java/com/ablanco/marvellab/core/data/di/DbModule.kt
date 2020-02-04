package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.data.db.CreateDatabase
import com.ablanco.marvellab.core.data.db.MarvelDatabase
import com.ablanco.marvellab.core.data.db.dao.CharacterComicCrossRefDao
import com.ablanco.marvellab.core.data.db.dao.CharactersDao
import com.ablanco.marvellab.core.data.db.dao.CharactersSearchDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
@Module
class DbModule {

    @Singleton
    @Provides
    fun providesDatabase(context: Context): MarvelDatabase = CreateDatabase(context)

    @Provides
    fun providesCharactersDao(db: MarvelDatabase): CharactersDao = db.charactersDao()

    @Provides
    fun providesCharactersSearchDao(db: MarvelDatabase): CharactersSearchDao =
        db.charactersSearchDao()

    @Provides
    fun providesCharacterComicCrossRefDao(db: MarvelDatabase): CharacterComicCrossRefDao =
        db.characterComicCrossRefDao()
}