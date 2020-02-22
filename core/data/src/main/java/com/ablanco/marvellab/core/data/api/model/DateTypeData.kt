package com.ablanco.marvellab.core.data.api.model

import com.squareup.moshi.Json

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-21.
 * MarvelLab.
 */
enum class DateTypeData {
    @Json(name = "onsaleDate")
    OnSaleDate,
    @Json(name = "focDate")
    FocDate,
    Unknown
}