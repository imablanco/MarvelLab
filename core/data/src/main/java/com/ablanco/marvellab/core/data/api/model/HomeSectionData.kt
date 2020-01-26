package com.ablanco.marvellab.core.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
data class HomeSectionData(
    @SerializedName("name") val name: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("type") val type: HomeSectionTypeData?,
    @SerializedName("order") val order: Int?
)