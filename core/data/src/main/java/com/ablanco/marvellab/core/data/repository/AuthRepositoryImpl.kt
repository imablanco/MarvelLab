package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.AuthApiDataSource
import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
class AuthRepositoryImpl @Inject constructor(private val authApiDataSource: AuthApiDataSource) :
    AuthRepository {

    override suspend fun isUserLogged(): Resource<Boolean> = authApiDataSource.isUserLogged()

    override suspend fun login(username: String, password: String): Resource<Boolean> =
        authApiDataSource.login(username, password)

    override suspend fun signUp(username: String, password: String): CompletableResource =
        authApiDataSource.signUp(username, password)

    override suspend fun logout() = authApiDataSource.logout()
}