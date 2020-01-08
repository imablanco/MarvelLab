package com.ablanco.marvellab.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
abstract class BaseViewModelFactory<T : ViewModel> : ViewModelProvider.Factory {

    protected abstract fun create(): T

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = create() as T
}