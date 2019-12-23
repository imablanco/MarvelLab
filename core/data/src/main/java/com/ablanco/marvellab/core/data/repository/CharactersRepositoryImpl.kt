package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.CharactersApiDataSource
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Álvaro Blanco Cabrero on 2019-12-23.
 * MarvelLab.
 */
class CharactersRepositoryImpl(
    private val apiDataSource: CharactersApiDataSource
) : CharactersRepository {

    override fun getCharacters(): Flow<List<Character>> = flow {
        emit(apiDataSource.getCharacters())
    }
}