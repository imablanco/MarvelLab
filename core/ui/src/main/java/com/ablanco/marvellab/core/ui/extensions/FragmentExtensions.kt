package com.ablanco.marvellab.core.ui.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-27.
 * MarvelLab.
 */

fun FragmentActivity.replace(@IdRes container: Int, fragment: Fragment) =
    supportFragmentManager
        .beginTransaction()
        .replace(container, fragment)
        .addToBackStack(null)
        .commit()


fun FragmentActivity.clearBackStack() {
    repeat(supportFragmentManager.backStackEntryCount) { supportFragmentManager.popBackStack() }
}

val FragmentActivity.isAtRoot: Boolean
    get() = supportFragmentManager.backStackEntryCount == 1