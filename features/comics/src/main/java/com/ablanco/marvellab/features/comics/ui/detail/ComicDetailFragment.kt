package com.ablanco.marvellab.features.comics.ui.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.BaseCollapsingToolbarFragment
import com.ablanco.marvellab.core.ui.GlideApp
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.navigation.fragmentNavigator
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.ablanco.marvellab.core.ui.views.GridSpacingItemDecorator
import com.ablanco.marvellab.features.comics.R
import com.ablanco.marvellab.features.comics.di.detail.DaggerComicDetailComponent
import com.ablanco.marvellab.features.comics.presentation.detail.ComicDetailViewModel
import com.ablanco.marvellab.features.comics.presentation.detail.ComicDetailViewModelFactory
import com.ablanco.marvellab.features.comics.presentation.detail.GoToCharacterDetailAction
import com.ablanco.marvellab.shared.navigation.Characters
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class ComicDetailFragment : BaseCollapsingToolbarFragment(R.layout.fragment_comic_detail) {

    override val toolbarConfig: ToolbarConfig by lazy { SimpleToolbarConfig() }
    override val getToolbarView: () -> CollapsingToolbarLayout = { collapsingToolbarLayout }

    @Inject
    lateinit var viewModelFactory: ComicDetailViewModelFactory

    private val viewModel: ComicDetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerComicDetailComponent.builder()
            .coreComponent(coreComponent)
            .comicId(arguments?.getString(ARG_COMIC_ID).orEmpty())
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {
        val adapter = ComicCharactersAdapter(viewModel::characterClicked)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvCharacters.layoutManager = layoutManager
        rvCharacters.adapter = adapter
        rvCharacters.addOnScrollListener(
            EndScrollListener(
                layoutManager,
                viewModel::loadCharacters
            )
        )
        rvCharacters.addItemDecoration(
            GridSpacingItemDecorator(
                requireContext().resources.getDimensionPixelSize(
                    R.dimen.comicDetailItemSpacing
                )
            )
        )

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
            collapsingToolbarLayout.title = state.comic?.title
            GlideApp.with(this)
                .load(state.comic?.coverImageUrl)
                .placeholder(R.drawable.ic_book_black_24dp)
                .into(ivComic)
            adapter.submitList(state.characters)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is GoToCharacterDetailAction ->
                    requireActivity().featureNavigator?.getFragment(Characters(action.characterId))
                        ?.let { fragmentNavigator?.navigate(it) }
            }
        })

        if (!isRestored) {
            viewModel.load()
        }
    }

    companion object {

        private const val ARG_COMIC_ID = "arg:comic_id"

        fun newInstance(comicId: String) = ComicDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_COMIC_ID, comicId)
            }
        }
    }
}