package com.ablanco.marvellab.core.data.di

import android.content.Context
import com.ablanco.marvellab.core.data.db.CreateDatabase
import com.ablanco.marvellab.core.data.db.MarvelDatabase
import com.ablanco.marvellab.core.data.db.dao.*
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
    fun providesComicsDao(db: MarvelDatabase): ComicsDao = db.comicsDao()

    @Provides
    fun providesComicsSearchDao(db: MarvelDatabase): ComicsSearchDao = db.comicsSearchDao()

    @Provides
    fun providesComicCharactersDao(db: MarvelDatabase): ComicCharactersDao = db.comicCharactersDao()

    @Provides
    fun providesCharacterComicsDao(db: MarvelDatabase): CharacterComicsDao = db.characterComicsDao()
}