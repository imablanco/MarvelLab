package com.ablanco.marvellab.core.di

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-09.
 * MarvelLab.
 */

val Context.coreComponent: CoreComponent
    get() = (applicationContext as? CoreComponentOwner)?.coreComponent
        ?: throw IllegalStateException("Application must implement CoreComponentOwner")

val Fragment.coreComponent: CoreComponent
    get() = requireContext().coreComponent