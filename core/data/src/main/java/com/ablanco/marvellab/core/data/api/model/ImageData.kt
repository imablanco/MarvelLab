package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
data class ImageData(
    @Json(name = "path") val path: String?,
    @Json(name = "extension") val extension: String?
)