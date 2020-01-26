package com.ablanco.marvellab.core.presentation

import java.util.regex.Pattern

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-26.
 * MarvelLab.
 */

object Patterns {

    val emailPattern: Pattern = Pattern.compile(
        """[a-zA-Z0-9+._%\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{0,25})+"""
    )
}

interface Validator<T> {

    fun isValid(value: T): Boolean
}

fun <T> Validator(validator: (T) -> Boolean): Validator<T> = object : Validator<T> {
    override fun isValid(value: T): Boolean = validator(value)
}

fun EmailValidator(): Validator<String> = Validator { Patterns.emailPattern.matcher(it).matches() }
fun MinLengthTextValidator(length: Int): Validator<String> = Validator { it.length >= length }