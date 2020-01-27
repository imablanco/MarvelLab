package com.ablanco.marvellab.core.data

import com.ablanco.marvellab.core.domain.model.Resource
import com.ablanco.marvellab.core.domain.model.toResource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */

val gson = GsonBuilder().create()

fun <T : Any> T.toJson(): String = gson.toJson(this)

inline fun <reified T : Any> String.fromJson(): Resource<T> =
    runCatching { gson.fromJson<T>(this, object : TypeToken<T>() {}.type) }.toResource()