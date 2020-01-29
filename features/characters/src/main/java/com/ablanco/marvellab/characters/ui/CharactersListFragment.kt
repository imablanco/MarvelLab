package com.ablanco.marvellab.characters.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ablanco.marvellab.characters.R
import com.ablanco.marvellab.characters.di.DaggerCharactersListComponent
import com.ablanco.marvellab.characters.presentation.CharactersListViewModel
import com.ablanco.marvellab.characters.presentation.CharactersListViewModelFactory
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.BaseToolbarFragment
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import kotlinx.android.synthetic.main.fragment_characters_list.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class CharactersListFragment : BaseToolbarFragment(R.layout.fragment_characters_list) {

    @Inject
    lateinit var viewModelFactory: CharactersListViewModelFactory

    private val viewModel: CharactersListViewModel by viewModels { viewModelFactory }

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(
            title = getString(R.string.characters_list_title)
        )
    }
    override val toolbarView: Toolbar by lazy { toolbar }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        DaggerCharactersListComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)

        val adapter = CharactersListAdapter()

        rvCharacters.layoutManager = GridLayoutManager(requireContext(), 2)
        rvCharacters.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
            adapter.submitList(state.characters)
        })

        if (!isRestored) {
            viewModel.load()
        }
    }

}