package com.ablanco.marvellab.features.profile.ui

import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.features.profile.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class ProfileFragment : BaseCollapsingToolbarFragment(R.layout.fragment_profile) {

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(
            title = getString(R.string.profile_title)
        )
    }

    override val toolbarView: CollapsingToolbarLayout by lazy { collapsingToolbarLayout }

}