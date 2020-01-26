package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.UserApiDataSource
import com.ablanco.marvellab.core.domain.model.CompletableResource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.user.User
import com.ablanco.marvellab.core.domain.model.user.UserUpdate
import com.ablanco.marvellab.core.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
class UserRepositoryImpl @Inject constructor(private val userApiDataSource: UserApiDataSource) :
    UserRepository {

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getUser(): Flow<Resource<User>> = userApiDataSource.getUser()

    @ExperimentalCoroutinesApi
    override suspend fun updateUser(userUpdate: UserUpdate): CompletableResource =
        userApiDataSource.updateUser(userUpdate)
}