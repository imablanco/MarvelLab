package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
data class BaseResponseData<T>(@Json(name = "data") val data: ResponseWrapperData<T>?)