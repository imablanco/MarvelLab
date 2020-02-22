package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
@JsonClass(generateAdapter = true)
data class DateData(
    @Json(name = "type") val type: DateTypeData?,
    @Json(name = "date") val date: Date?
)