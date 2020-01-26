package com.ablanco.marvellab.core.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */
data class HomeConfigData(@SerializedName("sections") val sections: List<HomeSectionData>?)