package com.ablanco.marvellab.core.data.di

import com.ablanco.marvellab.core.data.api.service.CharactersService
import com.ablanco.marvellab.core.data.api.service.ComicsService
import com.ablanco.marvellab.core.data.api.service.ServiceBuilder
import dagger.Module
import dagger.Provides

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
@Module
class ApiModule {

    @Provides
    fun providesCharactersService(serviceBuilder: ServiceBuilder): CharactersService =
        serviceBuilder.create()

    @Provides
    fun providesComicsService(serviceBuilder: ServiceBuilder): ComicsService =
        serviceBuilder.create()
}