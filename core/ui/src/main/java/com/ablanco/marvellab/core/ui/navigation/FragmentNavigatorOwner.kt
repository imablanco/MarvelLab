package com.ablanco.marvellab.core.ui.navigation

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */

interface FragmentNavigator {

    fun navigate(fragment: Fragment)

    fun popBackStack()

    fun clearBackStack()

    val isAtRoot: Boolean
}

interface FragmentNavigatorOwner {

    val fragmentNavigator: FragmentNavigator
}

class FragmentNavigatorImpl(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) : FragmentNavigator {

    override fun navigate(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .replace(container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popBackStack() {
        fragmentManager.popBackStack()
    }

    override fun clearBackStack() =
        repeat(fragmentManager.backStackEntryCount) { popBackStack() }

    override val isAtRoot: Boolean
        get() = fragmentManager.backStackEntryCount == 1
}

val Context.fragmentNavigator: FragmentNavigator?
    get() = (this as? FragmentNavigatorOwner)?.fragmentNavigator

val Fragment.fragmentNavigator: FragmentNavigator?
    get() = requireActivity().fragmentNavigator