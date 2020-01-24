package com.ablanco.marvellab.core.ui.extensions

import android.view.View

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-23.
 * MarvelLab.
 */

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.switchVisibility(condition: Boolean) = if (condition) visible() else gone()