package com.ablanco.marvellab.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-23.
 * MarvelLab.
 */

@FragmentScope
class FavoritesViewModelFactory @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FavoritesViewModel(favoritesRepository) as T
}

data class FavoritesViewState(
    val isLoading: Boolean = false,
    val favorites: List<Favorite> = emptyList()
) : ViewState

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : LoaderViewModel<FavoritesViewState, FavoritesViewAction>() {

    override val initialViewState: FavoritesViewState = FavoritesViewState()

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            favoritesRepository.getAllFavorites().collect {
                setState { copy(isLoading = false, favorites = it.getOrNull().orEmpty()) }
            }
        }
    }

    fun favoriteClicked(favorite: Favorite) {
        when (favorite.favoriteType) {
            FavoriteType.Character -> dispatchAction(GoToCharacterDetailAction(favorite.id))
            FavoriteType.Comic -> dispatchAction(GoToComicDetailViewAction(favorite.id))
        }
    }

    fun removeFavorite(favorite: Favorite) {
        launch {
            favoritesRepository.removeFavorite(favorite)
        }
    }
}