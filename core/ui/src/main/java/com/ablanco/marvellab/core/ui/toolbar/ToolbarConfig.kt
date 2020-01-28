package com.ablanco.marvellab.core.ui.toolbar

import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import com.ablanco.marvellab.core.ui.R

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
interface ToolbarConfig {
    val title: String?
    @get:DrawableRes
    val navigationIcon: Int?
    @get:MenuRes
    val menu: Int?
    val onMenuClickListener: ((MenuItem) -> Boolean)?
}

interface ToolbarConfigOwner {
    val toolbarConfig: ToolbarConfig
}

object NoOpToolbarConfig : ToolbarConfig {
    override val title: String? = null
    override val navigationIcon: Int? = null
    override val menu: Int? = null
    override val onMenuClickListener: ((MenuItem) -> Boolean)? = null
}

class SimpleToolbarConfig(
    override val title: String? = null,
    override val navigationIcon: Int? = R.drawable.ic_arrow_back_black_24dp,
    override val menu: Int? = null,
    override val onMenuClickListener: ((MenuItem) -> Boolean)? = null
) : ToolbarConfig