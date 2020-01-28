package com.ablanco.marvellab.core.ui.toolbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.ablanco.marvellab.core.ui.extensions.isAtRoot
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */

interface ToolbarOwner<T : View> {

    val toolbarView: T
}

interface ToolbarPlugin<T : View> {

    fun T.applyConfig(config: ToolbarConfig)
}

class SimpleToolbarPlugin(private val activity: FragmentActivity) : ToolbarPlugin<Toolbar> {

    private val getDrawable: (Int) -> Drawable? = { ContextCompat.getDrawable(activity, it) }

    override fun Toolbar.applyConfig(config: ToolbarConfig) {
        title = config.title
        setNavigationOnClickListener { activity.onBackPressed() }
        config.menu?.let(::inflateMenu)
        val navigationIcon = config.navigationIcon?.let(getDrawable)
        setNavigationIcon(if (!activity.isAtRoot) navigationIcon else null)
        config.onMenuClickListener?.let { listener -> setOnMenuItemClickListener { listener(it) } }
    }
}

class CollapsingToolbarPlugin(private val activity: FragmentActivity) :
    ToolbarPlugin<CollapsingToolbarLayout> {

    private val getDrawable: (Int) -> Drawable? = { ContextCompat.getDrawable(activity, it) }

    override fun CollapsingToolbarLayout.applyConfig(config: ToolbarConfig) {
        val toolbar = children.find { it is Toolbar } as? Toolbar
        title = config.title
        toolbar?.run {
            setNavigationOnClickListener { activity.onBackPressed() }
            config.menu?.let(::inflateMenu)
            val navigationIcon = config.navigationIcon?.let(getDrawable)
            setNavigationIcon(if (!activity.isAtRoot) navigationIcon else null)
            config.onMenuClickListener?.let { listener -> setOnMenuItemClickListener { listener(it) } }
        }
    }
}