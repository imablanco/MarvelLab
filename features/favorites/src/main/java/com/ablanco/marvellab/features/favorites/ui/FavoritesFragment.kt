package com.ablanco.marvellab.features.favorites.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.BaseToolbarFragment
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.navigation.fragmentNavigator
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.features.favorites.R
import com.ablanco.marvellab.features.favorites.databinding.FragmentFavoritesBinding
import com.ablanco.marvellab.features.favorites.di.DaggerFavoritesComponent
import com.ablanco.marvellab.features.favorites.presentation.FavoritesViewModel
import com.ablanco.marvellab.features.favorites.presentation.FavoritesViewModelFactory
import com.ablanco.marvellab.features.favorites.presentation.GoToCharacterDetailAction
import com.ablanco.marvellab.features.favorites.presentation.GoToComicDetailViewAction
import com.ablanco.marvellab.shared.navigation.Characters
import com.ablanco.marvellab.shared.navigation.Comics
import com.ablanco.marvellab.shared.navigation.featureNavigator
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */
class FavoritesFragment : BaseToolbarFragment(R.layout.fragment_favorites) {

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(title = getString(R.string.favorites_title))
    }
    override val getToolbarView: () -> Toolbar = { binding.toolbar }

    @Inject
    lateinit var favoritesViewModelFactory: FavoritesViewModelFactory

    private val viewModel: FavoritesViewModel by viewModels { favoritesViewModelFactory }

    private val binding: FragmentFavoritesBinding by binding(FragmentFavoritesBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerFavoritesComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        val adapter = FavoritesAdapter(viewModel::favoriteClicked, viewModel::removeFavorite)

        binding.rvFavorites.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        binding.rvFavorites.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            binding.viewLoading.switchVisibility(state.isLoading)
            adapter.submitList(state.favorites)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            val fragment = when (action) {
                is GoToCharacterDetailAction ->
                    requireContext().featureNavigator
                        ?.getFragment(Characters(action.characterId))
                is GoToComicDetailViewAction ->
                    requireContext().featureNavigator
                        ?.getFragment(Comics(action.comicId))
            }
            fragment?.let { fragmentNavigator?.navigate(it) }
        })

        if (!isRestored) {
            viewModel.load()
        }
    }
}