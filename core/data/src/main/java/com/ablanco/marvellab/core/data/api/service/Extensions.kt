package com.ablanco.marvellab.core.data.api.service

import com.ablanco.marvellab.core.data.api.model.BaseResponseData
import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.failOf
import com.ablanco.marvellab.core.domain.model.successOf

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */

val NullValueError = Throwable("Null value")

fun <T> BaseResponseData<T>.unwrapResult(): T? = data?.results?.firstOrNull()

fun <T> BaseResponseData<T>.unwrapResults(): List<T> = data?.results.orEmpty()

suspend fun <T> buildSingleResource(call: suspend () -> BaseResponseData<T>): Resource<T> =
    runCatching { call.invoke() }.fold(
        { response -> response.unwrapResult()?.let { successOf(it) } ?: failOf(NullValueError) },
        { error -> failOf(error) }
    )

suspend fun <T> buildListResource(call: suspend () -> BaseResponseData<T>): Resource<List<T>> =
    runCatching { call.invoke() }.fold(
        { response -> successOf(response.unwrapResults()) },
        { error -> failOf(error) }
    )