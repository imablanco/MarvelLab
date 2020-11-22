package com.ablanco.marvellab.features.comics.ui.detail

import android.os.Bundle
import android.view.MenuItem
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
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.ablanco.marvellab.core.ui.views.GridSpacingItemDecorator
import com.ablanco.marvellab.features.comics.R
import com.ablanco.marvellab.features.comics.databinding.FragmentComicDetailBinding
import com.ablanco.marvellab.features.comics.di.detail.DaggerComicDetailComponent
import com.ablanco.marvellab.features.comics.presentation.detail.ComicDetailViewModel
import com.ablanco.marvellab.features.comics.presentation.detail.ComicDetailViewModelFactory
import com.ablanco.marvellab.features.comics.presentation.detail.GoToCharacterDetailAction
import com.ablanco.marvellab.shared.navigation.Characters
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.google.android.material.appbar.CollapsingToolbarLayout
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class ComicDetailFragment : BaseCollapsingToolbarFragment(R.layout.fragment_comic_detail) {

    override val toolbarConfig: ToolbarConfig = SimpleToolbarConfig(
        menu = R.menu.menu_comic_detail,
        onMenuClickListener = {
            when (it.itemId) {
                R.id.action_fav -> viewModel.favoriteComicClicked()
            }
            true
        }
    )
    override val getToolbarView: () -> CollapsingToolbarLayout = { binding.collapsingToolbarLayout }

    private val menuActionView: MenuItem?
        get() = binding.toolbar.menu?.findItem(R.id.action_fav)

    @Inject
    lateinit var viewModelFactory: ComicDetailViewModelFactory

    private val viewModel: ComicDetailViewModel by viewModels { viewModelFactory }

    private val binding: FragmentComicDetailBinding by binding(FragmentComicDetailBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerComicDetailComponent.builder()
            .coreComponent(coreComponent)
            .comicId(arguments?.getString(ARG_COMIC_ID).orEmpty())
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {
        val adapter = ComicCharactersAdapter(
            viewModel::characterClicked,
            viewModel::favoriteCharacterClicked
        )
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacters.layoutManager = layoutManager
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.addOnScrollListener(
            EndScrollListener(
                layoutManager,
                viewModel::loadCharacters
            )
        )
        binding.rvCharacters.addItemDecoration(
            GridSpacingItemDecorator(
                requireContext().resources.getDimensionPixelSize(
                    R.dimen.comicDetailItemSpacing
                )
            )
        )

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            binding.viewLoading.switchVisibility(state.isLoading)
            binding.collapsingToolbarLayout.title = state.comic?.comic?.title
            GlideApp.with(this)
                .load(state.comic?.comic?.coverImageUrl)
                .placeholder(R.drawable.ic_book_black_24dp)
                .into(binding.ivComic)
            adapter.submitList(state.characters)
            val isFavorite = state.comic?.isFavorite == true
            menuActionView?.setIcon(
                if (isFavorite) R.drawable.ic_favorite_black_24dp
                else R.drawable.ic_favorite_border_black_24dp
            )
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