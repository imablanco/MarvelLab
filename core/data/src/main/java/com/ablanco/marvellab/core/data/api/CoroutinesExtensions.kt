package com.ablanco.marvellab.core.data.api

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume


/**
 * Created by Álvaro Blanco Cabrero on 26/02/2020.
 * MarvelLab.
 */

fun <T> CancellableContinuation<T>.resumeIfActive(value: T) {
    if (isActive) resume(value)
}