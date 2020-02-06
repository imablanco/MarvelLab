package com.ablanco.marvellab.characters.ui.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ablanco.marvellab.characters.R
import com.ablanco.marvellab.characters.di.detail.DaggerCharacterDetailComponent
import com.ablanco.marvellab.characters.presentation.detail.CharacterDetailViewModel
import com.ablanco.marvellab.characters.presentation.detail.CharacterDetailViewModelFactory
import com.ablanco.marvellab.characters.presentation.detail.GoToCharacterComicAction
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_characters_detail.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */
class CharacterDetailFragment : BaseCollapsingToolbarFragment(R.layout.fragment_characters_detail) {

    override val toolbarConfig: ToolbarConfig = SimpleToolbarConfig()
    override val getToolbarView: () -> CollapsingToolbarLayout = { collapsingToolbarLayout }

    @Inject
    lateinit var viewModelFactory: CharacterDetailViewModelFactory

    private val viewModel: CharacterDetailViewModel by viewModels { viewModelFactory }

    private val characterId: String by lazy {
        arguments?.getString(ARG_CHARACTER_ID).orEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCharacterDetailComponent
            .builder()
            .characterId(characterId)
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        val adapter = CharacterComicsAdapter(viewModel::characterComicClicked)
        val layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvComics.layoutManager = layoutManager
        rvComics.adapter = adapter
        rvComics.addOnScrollListener(EndScrollListener(layoutManager, viewModel::loadComics))

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
            collapsingToolbarLayout.title = state.character?.name
            tvDescription.text = state.character?.description
            adapter.submitList(state.comics)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is GoToCharacterComicAction -> {
                } //TODO
            }
        })

        if (!isRestored) {
            viewModel.load()
        }
    }

    companion object {
        private const val ARG_CHARACTER_ID = "arg:character_id"

        fun newInstance(characterId: String) = CharacterDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CHARACTER_ID, characterId)
            }
        }
    }
}