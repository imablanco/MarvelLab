package com.ablanco.marvellab.features.characters.ui.list

import android.os.Bundle
import android.widget.TextView
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.ui.tooling.preview.Preview
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.presentation.compose.ViewModelComposition
import com.ablanco.marvellab.core.presentation.compose.currentViewState
import com.ablanco.marvellab.core.ui.ComposeFragment
import com.ablanco.marvellab.core.ui.compose.MarvelLabTheme
import com.ablanco.marvellab.core.ui.compose.Toolbar
import com.ablanco.marvellab.features.characters.R
import com.ablanco.marvellab.features.characters.di.list.DaggerCharactersListComponent
import com.ablanco.marvellab.features.characters.presentation.list.CharactersListViewModel
import com.ablanco.marvellab.features.characters.presentation.list.CharactersListViewModelFactory
import com.ablanco.marvellab.features.characters.presentation.list.CharactersListViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-28.
 * MarvelLab.
 */
class CharactersListFragment : ComposeFragment() {

    @Inject
    lateinit var viewModelFactory: CharactersListViewModelFactory

    private val viewModel: CharactersListViewModel by viewModels { viewModelFactory }

/*    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(title = getString(R.string.characters_list_title))
    }
    override val getToolbarView: () -> Toolbar = { binding.toolbar }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCharactersListComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        /*val adapter = CharactersListAdapter(viewModel::characterClicked, viewModel::favoriteClicked)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCharacters.layoutManager = layoutManager
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.addOnScrollListener(EndScrollListener(layoutManager) { items ->
            viewModel.searchCharacters(binding.etSearch.textOrNull, items)
        })
        binding.rvCharacters.addItemDecoration(
            GridSpacingItemDecorator(
                requireContext().resources.getDimensionPixelSize(
                    R.dimen.charactersListItemSpacing
                )
            )
        )
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH ->
                    viewModel.searchCharacters(binding.etSearch.textOrNull)
            }
            false
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            binding.viewLoading.switchVisibility(state.isLoading)
            adapter.submitList(state.characters)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is GoToCharacterDetail -> fragmentNavigator?.navigate(
                    CharacterDetailFragment.newInstance(action.characterId)
                )
            }
        })

        if (!isRestored) {
            viewModel.load()
        }*/
    }

    private val TextView.textOrNull: String?
        get() = text.toString().takeIf { it.isNotBlank() }


    @Composable
    override fun compose() = ViewModelComposition(viewModel = viewModel) { state ->
        CharactersListContent()
    }

}

@Preview
@Composable
fun CharactersListContent() = MarvelLabTheme {
    Scaffold(topBar = { Toolbar(title = stringResource(R.string.characters_list_title)) }) {
    }
}