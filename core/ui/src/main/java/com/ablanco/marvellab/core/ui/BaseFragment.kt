package com.ablanco.marvellab.core.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ablanco.marvellab.core.ui.toolbar.CollapsingToolbarPlugin
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarPlugin
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfigOwner
import com.ablanco.marvellab.core.ui.toolbar.ToolbarOwner
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var isRestored = false

    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onViewReady(savedInstanceState, isRestored)
        isRestored = true
    }

    abstract fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean)
}

abstract class BaseToolbarFragment(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    ToolbarConfigOwner, ToolbarOwner<Toolbar> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SimpleToolbarPlugin(requireActivity()).run { getToolbarView().applyConfig(toolbarConfig) }
    }
}

abstract class BaseCollapsingToolbarFragment(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    ToolbarConfigOwner, ToolbarOwner<CollapsingToolbarLayout> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CollapsingToolbarPlugin(requireActivity()).run { getToolbarView().applyConfig(toolbarConfig) }
    }
}

