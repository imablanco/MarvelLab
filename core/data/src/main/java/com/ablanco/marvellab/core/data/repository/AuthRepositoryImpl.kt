package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.AuthApiDataSource
import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.auth.Login
import com.ablanco.marvellab.core.domain.model.auth.SignUp
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
class AuthRepositoryImpl @Inject constructor(private val authApiDataSource: AuthApiDataSource) :
    AuthRepository {

    override suspend fun isUserLogged(): Resource<Boolean> = authApiDataSource.isUserLogged()

    override suspend fun login(login: Login): Resource<Boolean> =
        authApiDataSource.login(login)

    @ExperimentalCoroutinesApi
    override suspend fun signUp(signUp: SignUp): CompletableResource =
        authApiDataSource.signUp(signUp)

    override suspend fun logout() = authApiDataSource.logout()
}