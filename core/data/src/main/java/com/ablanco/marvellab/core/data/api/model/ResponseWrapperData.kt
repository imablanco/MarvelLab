package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
data class ResponseWrapperData<T>(@Json(name = "results") val results: List<T>)