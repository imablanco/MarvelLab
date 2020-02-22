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
import com.ablanco.marvellab.core.ui.views.EndScrollListener
import com.ablanco.marvellab.features.comics.R
import com.ablanco.marvellab.features.comics.di.list.DaggerComicsListComponent
import com.ablanco.marvellab.features.comics.presentation.list.ComicsListViewModel
import com.ablanco.marvellab.features.comics.presentation.list.ComicsListViewModelFactory
import com.ablanco.marvellab.features.comics.presentation.list.GoToComicDetailAction
import com.ablanco.marvellab.features.comics.ui.detail.ComicDetailFragment
import kotlinx.android.synthetic.main.fragment_comics_list.*
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */
class ComicsListFragment : BaseToolbarFragment(R.layout.fragment_comics_list) {

    override val toolbarConfig: ToolbarConfig by lazy {
        SimpleToolbarConfig(getString(R.string.comics_list_title))
    }
    override val getToolbarView: () -> Toolbar = { toolbar }

    @Inject
    lateinit var comicsListViewModelFactory: ComicsListViewModelFactory

    private val viewModel: ComicsListViewModel by viewModels { comicsListViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerComicsListComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    override fun onViewReady(savedInstanceState: Bundle?, isRestored: Boolean) {

        val adapter = ComicsListAdapter(viewModel::comicClicked)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvComics.layoutManager = layoutManager
        rvComics.adapter = adapter
        rvComics.addOnScrollListener(EndScrollListener(layoutManager) { items ->
            viewModel.searchComics(etSearch.textOrNull, items)
        })
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH ->
                    viewModel.searchComics(etSearch.textOrNull)
            }
            false
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            viewLoading.switchVisibility(state.isLoading)
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