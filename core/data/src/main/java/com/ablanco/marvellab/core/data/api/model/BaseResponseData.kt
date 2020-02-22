package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
@JsonClass(generateAdapter = true)
data class BaseResponseData<T>(@Json(name = "data") val data: ResponseWrapperData<T>?)