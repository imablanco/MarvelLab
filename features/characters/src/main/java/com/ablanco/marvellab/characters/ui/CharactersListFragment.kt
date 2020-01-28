package com.ablanco.marvellab.characters.ui

import androidx.appcompat.widget.Toolbar
import com.ablanco.marvellab.characters.R
import com.ablanco.marvellab.core.ui.BaseToolbarFragment
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import kotlinx.android.synthetic.main.fragment_characters_list.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class CharactersListFragment : BaseToolbarFragment(R.layout.fragment_characters_list) {

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(
            title = getString(R.string.characters_list_title)
        )
    }
    override val toolbarView: Toolbar by lazy { toolbar }
}