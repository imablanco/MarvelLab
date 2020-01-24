package com.ablanco.marvellab.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
abstract class BaseViewModel<V : ViewState, A : ViewAction> : ViewModel() {

    protected abstract val initialViewState: V

    private val _viewState: MutableLiveData<V> by lazy { mutableLiveData(initialViewState) }
    private val _viewAction: LiveEvent<A> = LiveEvent()

    val viewState: LiveData<V> by lazy { _viewState }
    val viewAction: LiveData<A> by lazy { _viewAction }

    protected fun getState(): V = viewState.value ?: throw IllegalStateException()

    protected fun setState(reducer: V.() -> V) {
        _viewState.value = getState().reducer()
    }

    protected fun dispatchAction(action: A) {
        _viewAction.value = action
    }

    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(block = block)
}