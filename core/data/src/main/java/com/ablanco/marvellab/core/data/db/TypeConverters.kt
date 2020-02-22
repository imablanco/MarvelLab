package com.ablanco.marvellab.core.data.db

import androidx.room.TypeConverter
import com.ablanco.marvellab.core.data.fromJsonArray
import com.ablanco.marvellab.core.data.toJson
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-04.
 * MarvelLab.
 */

class StringListTypeConverter {

    @TypeConverter
    fun List<String>.convert(): String = toJson()

    @TypeConverter
    fun String.convert(): List<String> = fromJsonArray<String>().getOrNull().orEmpty()

    @TypeConverter
    fun Date.convert(): Long = time

    @TypeConverter
    fun Long.convert(): Date = Date(this)
}