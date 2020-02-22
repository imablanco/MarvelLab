package com.ablanco.marvellab.core.data

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.toResource
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */

val moshi = Moshi.Builder().build()

inline fun <reified T : Any> T.toJson(): String = moshi.adapter(T::class.java).toJson(this)

inline fun <reified T : Any> String.fromJsonArray(): Resource<List<T>> =
    runCatching {
        val type = Types.newParameterizedType(
            List::class.java,
            T::class.java
        )
        moshi.adapter<List<T>>(type).fromJson(this).orEmpty()
    }.toResource()

inline fun <reified T : Any> String.fromJson(): Resource<T> =
    runCatching {
        moshi.adapter<T>(T::class.java).fromJson(this)
            ?: throw IllegalStateException("Value can not be null")
    }.toResource()