package com.ablanco.marvellab.core.test

import androidx.lifecycle.Observer

/**
 * Created by Álvaro Blanco Cabrero on 02/05/2020.
 * MarvelLab.
 */

class TestObserver<T> : Observer<T> {

    private val _values: MutableList<T> = mutableListOf()

    val values: List<T> get() = _values

    val firstValue: T get() = values.first()
    val secondValue: T get() = values[1]
    val thirdValue: T get() = values[2]
    val fourthValue: T get() = values[3]
    val fifthValue: T get() = values[4]

    override fun onChanged(t: T) {
        _values.add(t)
    }

    operator fun component1(): T = firstValue
    operator fun component2(): T = secondValue
    operator fun component3(): T = thirdValue
    operator fun component4(): T = fourthValue
    operator fun component5(): T = fifthValue
}
