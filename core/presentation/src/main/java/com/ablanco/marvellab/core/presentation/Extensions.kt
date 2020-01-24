package com.ablanco.marvellab.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */

fun <T> mutableLiveData(initialValue: T): MutableLiveData<T> =
    MutableLiveData<T>().apply { value = initialValue }

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    crossinline getFactory: () -> ViewModelProvider.Factory? = { null }
) = lazy { ViewModelProviders.of(this, getFactory())[T::class.java] }

inline fun <reified T : ViewModel> Fragment.viewModel(
    crossinline getFactory: () -> ViewModelProvider.Factory? = { null }
) = lazy { ViewModelProviders.of(this, getFactory())[T::class.java] }