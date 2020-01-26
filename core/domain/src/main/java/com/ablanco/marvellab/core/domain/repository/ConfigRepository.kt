package com.ablanco.marvellab.core.domain.repository

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.config.HomeConfig

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
interface ConfigRepository {

    suspend fun getHomeConfig(): Resource<HomeConfig>
}