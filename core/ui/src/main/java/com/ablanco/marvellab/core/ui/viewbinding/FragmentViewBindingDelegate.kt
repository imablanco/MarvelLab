package com.ablanco.marvellab.core.ui.viewbinding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Álvaro Blanco Cabrero on 01/03/2020.
 * MarvelLab.
 */
class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val bind: (View) -> T
) : ReadOnlyProperty<Any, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer {
            fragment.viewLifecycleOwner.lifecycle.addObserver(this)
        })
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding = null
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return binding ?: run {
            val rootView = fragment.view ?: throw IllegalStateException()
            bind(rootView).also { binding = it }
        }
    }
}

fun <T : ViewBinding> Fragment.binding(bind: (View) -> T) =
    FragmentViewBindingDelegate(this, bind)

fun <T : ViewBinding> Activity.inflate(inflate: (LayoutInflater) -> T) = lazy {
    inflate(layoutInflater)
}

fun <T : ViewBinding> Activity.binding(bind: (View) -> T) = lazy {
    bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
}