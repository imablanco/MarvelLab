package com.ablanco.marvellab.core.presentation

import androidx.lifecycle.MutableLiveData

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */

fun <T> mutableLiveData(initialValue: T): MutableLiveData<T> =
    MutableLiveData<T>().apply { value = initialValue }