package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.user.User
import com.ablanco.marvellab.core.domain.model.user.UserUpdate
import kotlinx.coroutines.flow.Flow

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
interface UserRepository {

    fun getUser(): Flow<Resource<User>>

    suspend fun updateUser(userUpdate: UserUpdate): CompletableResource
}