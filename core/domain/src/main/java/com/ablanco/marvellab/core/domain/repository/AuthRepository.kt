package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.auth.Login
import com.ablanco.marvellab.core.domain.model.auth.SignUp

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */
interface AuthRepository {

    suspend fun isUserLogged(): Resource<Boolean>

    suspend fun login(login: Login): Resource<Boolean>

    suspend fun signUp(signUp: SignUp): CompletableResource

    suspend fun logout(): CompletableResource
}