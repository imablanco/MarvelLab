package com.ablanco.marvellab.core.ui.toolbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import com.ablanco.marvellab.core.ui.navigation.fragmentNavigator
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */

interface ToolbarOwner<T : View> {

    val getToolbarView: () -> T
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
        val isAtRoot = activity.fragmentNavigator?.isAtRoot ?: false
        setNavigationIcon(if (!isAtRoot) navigationIcon else null)
        config.onMenuClickListener?.let { listener -> setOnMenuItemClickListener { listener(it) } }
    }
}

class CollapsingToolbarPlugin(activity: FragmentActivity) : ToolbarPlugin<CollapsingToolbarLayout> {

    private val toolbarPlugin = SimpleToolbarPlugin(activity)

    override fun CollapsingToolbarLayout.applyConfig(config: ToolbarConfig) {
        val toolbar = children.find { it is Toolbar } as? Toolbar
        title = config.title
        toolbarPlugin.run { toolbar?.applyConfig(config) }
    }
}