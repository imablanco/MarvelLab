package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
@JsonClass(generateAdapter = true)
data class HomeConfigData(@Json(name = "sections") val sections: List<HomeSectionData>?)