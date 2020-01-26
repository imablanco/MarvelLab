package com.ablanco.marvellab.core.domain.model

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-10.
 * MarvelLab.
 */

typealias CompletableResource = Resource<Unit>

sealed class Resource<A> {

    fun getOrNull(): A? = (this as Success<A>?)?.value

    fun <B> map(mapper: (A) -> B): Resource<B> = when (this) {
        is Success -> successOf(mapper(value))
        is Fail -> failOf(error)
    }

    fun <C> fold(success: (A) -> C, fail: (Throwable) -> C) =
        when (this) {
            is Success -> success(value)
            is Fail -> fail(error)
        }
}

class Success<A>(val value: A) : Resource<A>()
class Fail<A>(val error: Throwable) : Resource<A>()

fun <T> successOf(value: T): Resource<T> = Success(value)
fun <T> failOf(error: Throwable): Resource<T> = Fail(error)
fun completed(): CompletableResource = Success(Unit)
fun failed(error: Throwable): CompletableResource = failOf(error)

fun <T> Result<T>.toResource(): Resource<T> = fold({ successOf(it) }, { failOf(it) })

fun <T> Resource<T>.ignore(): CompletableResource = map { Unit }