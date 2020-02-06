package com.ablanco.marvellab.features.comics.presentation.list

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.core.presentation.autoCancelableJob
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */

@FragmentScope
class ComicsListViewModelFactory @Inject constructor(
    private val comicsRepository: ComicsRepository
) : BaseViewModelFactory<ComicsListViewModel>() {

    override fun create(): ComicsListViewModel =
        ComicsListViewModel(comicsRepository)
}


data class ComicsListViewState(
    val isLoading: Boolean = false,
    val comics: List<Comic> = emptyList()
) : ViewState

class ComicsListViewModel(private val comicsRepository: ComicsRepository) :
    LoaderViewModel<ComicsListViewState, ComicsListViewAction>() {

    override val initialViewState: ComicsListViewState = ComicsListViewState()

    private var searchJob by autoCancelableJob()

    override fun load() = searchComics()

    fun searchComics(search: String? = null, offset: Int = 0) {
        searchJob = launch {
            setState { copy(isLoading = true) }
            comicsRepository.searchComics(search, offset).collect {
                setState { copy(isLoading = false, comics = it.getOrNull().orEmpty()) }
            }
        }
    }

    fun comicClicked(comic: Comic) = dispatchAction(
        GoToComicDetailAction(
            comic.id
        )
    )
}