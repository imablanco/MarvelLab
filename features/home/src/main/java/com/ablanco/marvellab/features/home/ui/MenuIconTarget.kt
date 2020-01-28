package com.ablanco.marvellab.features.home.ui

import android.graphics.drawable.Drawable
import android.view.MenuItem
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class MenuIconTarget(private val menuItem: MenuItem) : CustomTarget<Drawable>() {

    override fun onLoadCleared(placeholder: Drawable?) = Unit

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        menuItem.icon = resource
    }
}