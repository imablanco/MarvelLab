package com.ablanco.marvellab.characters.ui.detail

import android.os.Bundle
import com.ablanco.marvellab.characters.R
import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_characters_detail.*

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */
class CharacterDetailFragment : BaseCollapsingToolbarFragment(R.layout.fragment_characters_detail) {

    override val toolbarConfig: ToolbarConfig = SimpleToolbarConfig()
    override val toolbarView: CollapsingToolbarLayout by lazy { collapsingToolbarLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO inject deps
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {
    }

    companion object {
        fun newInstance(characterId: String) = CharacterDetailFragment().apply {
            //TODO
        }
    }
}