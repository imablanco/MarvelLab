package com.ablanco.marvellab.core.data.api.service

import com.ablanco.marvellab.core.data.di.ApiBaseUrl
import com.ablanco.marvellab.core.data.di.ApiKey
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
class ServiceBuilder @Inject constructor(
    @ApiBaseUrl private val baseUrl: String,
    @ApiKey apiKey: String
) {

    val retrofit: Retrofit by lazy {
        val apiClient = OkHttpClient.Builder()
            .addInterceptor(ApiAuthInterceptor(apiKey))
            .build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(apiClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}