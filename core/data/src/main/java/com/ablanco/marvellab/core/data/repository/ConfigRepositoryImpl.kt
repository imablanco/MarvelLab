package com.ablanco.marvellab.core.data.repository

import com.ablanco.marvellab.core.data.api.ConfigApiDataSource
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.config.HomeConfig
import com.ablanco.marvellab.core.domain.repository.ConfigRepository
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
class ConfigRepositoryImpl @Inject constructor(private val configApiDataSource: ConfigApiDataSource) :
    ConfigRepository {

    override suspend fun getHomeConfig(): Resource<HomeConfig> = configApiDataSource.getHomeConfig()
}