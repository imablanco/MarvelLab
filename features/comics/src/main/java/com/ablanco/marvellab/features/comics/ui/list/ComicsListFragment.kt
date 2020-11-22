package com.ablanco.marvellab.features.comics.ui.list

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ablanco.marvellab.core.di.coreComponent
import com.ablanco.marvellab.core.ui.BaseToolbarFragment
import com.ablanco.marvellab.core.ui.extensions.switchVisibility
import com.ablanco.marvellab.core.ui.navigation.fragmentNavigator
import com.ablanco.marvellab.core.ui.toolbar.SimpleToolbarConfig
import com.ablanco.marvellab.core.ui.toolbar.ToolbarConfig
import com.ablanco.marvellab.core.ui.viewbinding.binding
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.ablanco.marvellab.features.comics.R
import com.ablanco.marvellab.features.comics.databinding.FragmentComicsListBinding
import com.ablanco.marvellab.features.comics.di.list.DaggerComicsListComponent
import com.ablanco.marvellab.features.comics.presentation.list.ComicsListViewModel
import com.ablanco.marvellab.features.comics.presentation.list.ComicsListViewModelFactory
import com.ablanco.marvellab.features.comics.presentation.list.GoToComicDetailAction
import com.ablanco.marvellab.features.comics.ui.detail.ComicDetailFragment
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class ComicsListFragment : BaseToolbarFragment(R.layout.fragment_comics_list) {

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(getString(R.string.comics_list_title))
    }
    override val getToolbarView: () -> Toolbar = { binding.toolbar }

    @Inject
    lateinit var comicsListViewModelFactory: ComicsListViewModelFactory

    private val viewModel: ComicsListViewModel by viewModels { comicsListViewModelFactory }

    private val binding: FragmentComicsListBinding by binding(FragmentComicsListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerComicsListComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        val adapter = ComicsListAdapter(viewModel::comicClicked, viewModel::favoriteClicked)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvComics.layoutManager = layoutManager
        binding.rvComics.adapter = adapter
        binding.rvComics.addOnScrollListener(EndScrollListener(layoutManager) { items ->
            viewModel.searchComics(binding.etSearch.textOrNull, items)
        })
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> viewModel.searchComics(binding.etSearch.textOrNull)
            }
            false
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            binding.viewLoading.switchVisibility(state.isLoading)
            adapter.submitList(state.comics)
        })

        viewModel.viewAction.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is GoToComicDetailAction ->
                    fragmentNavigator?.navigate(ComicDetailFragment.newInstance(action.comicId))
            }
        })

        if (!isRestored) {
            viewModel.load()
        }
    }

    private val TextView.textOrNull: String?
        get() = text.toString().takeIf { it.isNotBlank() }
}