package com.ablanco.marvellab.core.data.api.service

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
class LooseDateAdapter : JsonAdapter<Date>() {

    private val delegate: Rfc3339DateJsonAdapter by lazy {
        Rfc3339DateJsonAdapter()
    }

    override fun fromJson(reader: JsonReader): Date? {
        return runCatching { delegate.fromJson(reader) }.fold({ it }, { null })
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        runCatching { delegate.toJson(writer, value) }
    }
}