package com.ablanco.marvellab.features.comics.presentation.list

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.extensions.toFavorite
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.core.presentation.autoCancelableJob
import com.ablanco.marvellab.features.comics.presentation.common.ComicPresentation
import com.ablanco.marvellab.features.comics.presentation.common.mapComicPresentationsWithFavorites
import com.ablanco.marvellab.features.comics.presentation.common.mapComicsWithFavorites
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */

@FragmentScope
class ComicsListViewModelFactory @Inject constructor(
    private val comicsRepository: ComicsRepository,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModelFactory<ComicsListViewModel>() {

    override fun create(): ComicsListViewModel =
        ComicsListViewModel(comicsRepository, favoritesRepository)
}


data class ComicsListViewState(
    val isLoading: Boolean = false,
    val comics: List<ComicPresentation> = emptyList()
) : ViewState

class ComicsListViewModel(
    private val comicsRepository: ComicsRepository,
    private val favoritesRepository: FavoritesRepository
) : LoaderViewModel<ComicsListViewState, ComicsListViewAction>() {

    override val initialViewState: ComicsListViewState = ComicsListViewState()

    private var searchJob by autoCancelableJob()

    private var favorites: List<Favorite> = emptyList()

    override fun load() {
        launch {
            favoritesRepository.getAllFavorites().collect { resource ->
                favorites = resource.getOrNull().orEmpty()
                setState {
                    copy(
                        comics = comics.mapComicPresentationsWithFavorites(favorites)
                    )
                }
            }
        }
        searchComics()
    }

    fun searchComics(search: String? = null, offset: Int = 0) {
        searchJob = launch {
            setState { copy(isLoading = true) }
            comicsRepository.searchComics(search, offset).collect { resource ->
                val comics = resource.getOrNull()
                    ?.mapComicsWithFavorites(favorites)
                    .orEmpty()
                setState { copy(isLoading = false, comics = comics) }
            }
        }
    }

    fun comicClicked(comic: ComicPresentation) = dispatchAction(
        GoToComicDetailAction(comic.comic.id)
    )

    fun favoriteClicked(item: ComicPresentation) {
        launch {
            val favorite = item.comic.toFavorite()
            if (item.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }
}