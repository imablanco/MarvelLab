package com.ablanco.marvellab.core.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
enum class HomeSectionTypeData {
    @SerializedName("0")
    Characters,
    @SerializedName("1")
    Comics,
    @SerializedName("2")
    Profile
}