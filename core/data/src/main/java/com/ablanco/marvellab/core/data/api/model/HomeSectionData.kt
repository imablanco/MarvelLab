package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
@JsonClass(generateAdapter = true)
data class HomeSectionData(
    @Json(name = "name") val name: String?,
    @Json(name = "icon") val icon: String?,
    @Json(name = "type") val type: HomeSectionTypeData?,
    @Json(name = "order") val order: Int?
)