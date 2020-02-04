package com.ablanco.marvellab.core.data.db

import androidx.room.TypeConverter
import com.ablanco.marvellab.core.data.fromJson
import com.ablanco.marvellab.core.data.toJson

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-04.
 * MarvelLab.
 */

class StringListTypeConverter{

    @TypeConverter
    fun List<String>.convert(): String = toJson()

    @TypeConverter
    fun String.convert(): List<String> = fromJson<List<String>>().getOrNull().orEmpty()
}