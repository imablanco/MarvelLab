package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
interface AuthRepository {

    suspend fun isUserLogged(): Resource<Boolean>

    suspend fun login(username: String, password: String): Resource<Boolean>

    suspend fun signUp(username: String, password: String): CompletableResource

    suspend fun logout(): CompletableResource
}