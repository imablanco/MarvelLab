package com.ablanco.marvellab.core.presentation

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
abstract class LoaderViewModel<V : ViewState, A : ViewAction> : BaseViewModel<V, A>() {

    abstract fun load()
}