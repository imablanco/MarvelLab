package com.ablanco.marvellab.core.ui.toolbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
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

class SimpleToolbarPlugin(activity: FragmentActivity) : ToolbarPlugin<Toolbar> {

    private val isRoot = activity.supportFragmentManager.backStackEntryCount == 1
    private val getDrawable: (Int) -> Drawable? = { ContextCompat.getDrawable(activity, it) }

    override fun Toolbar.applyConfig(config: ToolbarConfig) {
        title = config.title
        config.menu?.let(::inflateMenu)
        val navigationIcon = config.navigationIcon?.let(getDrawable)
        setNavigationIcon(if (isRoot) navigationIcon else null)
        config.onMenuClickListener?.let(::setOnMenuItemClickListener)
    }
}

class CollapsingToolbarPlugin(activity: FragmentActivity) : ToolbarPlugin<CollapsingToolbarLayout> {

    private val isRoot = activity.supportFragmentManager.backStackEntryCount == 1
    private val getDrawable: (Int) -> Drawable? = { ContextCompat.getDrawable(activity, it) }

    override fun CollapsingToolbarLayout.applyConfig(config: ToolbarConfig) {
        val toolbar = children.find { it is Toolbar } as? Toolbar
        title = config.title
        toolbar?.run {
            config.menu?.let(::inflateMenu)
            val navigationIcon = config.navigationIcon?.let(getDrawable)
            setNavigationIcon(if (isRoot) navigationIcon else null)
            config.onMenuClickListener?.let(::setOnMenuItemClickListener)
        }
    }
}


fun FragmentActivity.toolbarPlugin() = SimpleToolbarPlugin(this)
fun FragmentActivity.collapsingToolbarPlugin() = CollapsingToolbarPlugin(this)