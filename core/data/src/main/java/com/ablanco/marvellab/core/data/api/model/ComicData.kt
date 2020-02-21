package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
data class ComicData(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val title: String?,
    @Json(name = "thumbnail") val thumbnail: ImageData?,
    @Json(name = "dates") val dates: List<DateData>
)