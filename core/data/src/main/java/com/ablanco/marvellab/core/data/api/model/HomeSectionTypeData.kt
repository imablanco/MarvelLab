package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
enum class HomeSectionTypeData {
    @Json(name = "0")
    Characters,
    @Json(name = "1")
    Comics,
    @Json(name = "2")
    Profile
}