package com.ablanco.marvellab.features.characters.ui.detail

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
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.ablanco.marvellab.features.characters.R
import com.ablanco.marvellab.features.characters.di.detail.DaggerCharacterDetailComponent
import com.ablanco.marvellab.features.characters.presentation.detail.CharacterDetailViewModel
import com.ablanco.marvellab.features.characters.presentation.detail.CharacterDetailViewModelFactory
import com.ablanco.marvellab.features.characters.presentation.detail.GoToCharacterComicAction
import com.ablanco.marvellab.shared.navigation.Comics
import com.ablanco.marvellab.shared.navigation.featureNavigator
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_character_detail.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */
class CharacterDetailFragment : BaseCollapsingToolbarFragment(R.layout.fragment_character_detail) {

    override val toolbarConfig: ToolbarConfig = SimpleToolbarConfig(
        menu = R.menu.menu_character_detail,
        onMenuClickListener = {
            when (it.itemId) {
                R.id.action_fav -> viewModel.favoriteCharacterClicked()
            }
            true
        }
    )
    override val getToolbarView: () -> CollapsingToolbarLayout = { collapsingToolbarLayout }

    private val menuActionView: MenuItem?
        get() = toolbar.menu?.findItem(R.id.action_fav)

    @Inject
    lateinit var viewModelFactory: CharacterDetailViewModelFactory

    private val viewModel: CharacterDetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerCharacterDetailComponent
            .builder()
            .characterId(arguments?.getString(ARG_CHARACTER_ID).orEmpty())
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        val adapter = CharacterComicsAdapter(
            viewModel::characterComicClicked,
            viewModel::favoriteComicClicked
        )
        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvComics.layoutManager = layoutManager
        rvComics.adapter = adapter
        rvComics.addOnScrollListener(EndScrollListener(layoutManager, viewModel::loadComics))

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
            GlideApp.with(this)
                .load(state.character?.character?.imageUrl)
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(ivCharacter)
            collapsingToolbarLayout.title = state.character?.character?.name
            tvDescription.text = state.character?.character?.description
            tvDescription.switchVisibility(!state.character?.character?.description.isNullOrBlank())
            val isFavorite = state.character?.isFavorite == true
            menuActionView?.setIcon(
                if (isFavorite) R.drawable.ic_favorite_black_24dp
                else R.drawable.ic_favorite_border_black_24dp
            )
            adapter.submitList(state.comics)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is GoToCharacterComicAction ->
                    requireActivity().featureNavigator?.getFragment(Comics(action.comicId))?.let {
                        fragmentNavigator?.navigate(it)
                    }
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