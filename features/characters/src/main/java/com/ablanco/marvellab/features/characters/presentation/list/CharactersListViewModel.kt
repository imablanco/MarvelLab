package com.ablanco.marvellab.features.characters.presentation.list

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.model.favorites.FavoriteType
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.core.presentation.autoCancelableJob
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */

@FragmentScope
class CharactersListViewModelFactory @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModelFactory<CharactersListViewModel>() {
    override fun create(): CharactersListViewModel =
        CharactersListViewModel(
            charactersRepository,
            favoritesRepository
        )
}


data class CharactersListViewState(
    val isLoading: Boolean = false,
    val characters: List<CharacterPresentation> = emptyList()
) : ViewState

class CharactersListViewModel(
    private val charactersRepository: CharactersRepository,
    private val favoritesRepository: FavoritesRepository
) : LoaderViewModel<CharactersListViewState, CharactersListViewAction>() {

    override val initialViewState: CharactersListViewState =
        CharactersListViewState()

    private var searchJob by autoCancelableJob()

    private var favorites: List<Favorite> = emptyList()

    override fun load() {
        launch {
            favoritesRepository.getAllFavorites().collect { resource ->
                favorites = resource.getOrNull().orEmpty()
                setState { copy(characters = characters.mapWithFavorites(favorites)) }
            }
        }
        searchCharacters()
    }

    fun searchCharacters(search: String? = null, offset: Int = 0) {
        searchJob = launch {
            setState { copy(isLoading = true) }
            charactersRepository.searchCharacters(search, offset).collect { resource ->
                val characters = resource.getOrNull()
                    ?.map { CharacterPresentation(it, false) }
                    ?.mapWithFavorites(favorites)
                    .orEmpty()
                setState { copy(isLoading = false, characters = characters) }
            }
        }
    }

    fun characterClicked(character: CharacterPresentation) = dispatchAction(
        GoToCharacterDetail(character.character.id)
    )

    fun favoriteClicked(item: CharacterPresentation) {
        launch {
            val character = item.character
            val favorite = Favorite(
                character.id,
                character.name,
                character.imageUrl,
                FavoriteType.Character
            )
            if (item.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }

    private fun List<CharacterPresentation>.mapWithFavorites(
        favorites: List<Favorite>
    ): List<CharacterPresentation> {
        val favoritesIds = favorites
            .filter { it.favoriteType == FavoriteType.Character }
            .map(Favorite::id)

        return map { it.copy(isFavorite = it.character.id in favoritesIds) }
    }
}