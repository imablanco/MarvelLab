package com.ablanco.marvellab.features.comics.presentation.detail

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.extensions.toFavorite
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.features.comics.di.detail.ComicId
import com.ablanco.marvellab.features.comics.presentation.common.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-06.
 * MarvelLab.
 */

@FragmentScope
class ComicDetailViewModelFactory @Inject constructor(
    @ComicId private val comicId: String,
    private val comicsRepository: ComicsRepository,
    private val charactersRepository: CharactersRepository,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModelFactory<ComicDetailViewModel>() {

    override fun create(): ComicDetailViewModel =
        ComicDetailViewModel(comicId, comicsRepository, charactersRepository, favoritesRepository)
}

data class ComicDetailViewState(
    val isLoading: Boolean = false,
    val comic: ComicPresentation? = null,
    val characters: List<CharacterPresentation> = emptyList()
) : ViewState

class ComicDetailViewModel(
    private val comicId: String,
    private val comicsRepository: ComicsRepository,
    private val charactersRepository: CharactersRepository,
    private val favoritesRepository: FavoritesRepository
) : LoaderViewModel<ComicDetailViewState, ComicDetailViewAction>() {

    override val initialViewState: ComicDetailViewState = ComicDetailViewState()

    private var favorites: List<Favorite> = emptyList()

    override fun load() {
        launch {
            favoritesRepository.getAllFavorites().collect { resource ->
                favorites = resource.getOrNull().orEmpty()
                val characterId = getState().comic?.comic?.id
                val favoriteIds = favorites.map(Favorite::id)
                setState {
                    copy(
                        comic = comic?.copy(isFavorite = characterId in favoriteIds),
                        characters = characters.mapCharacterPresentationsWithFavorites(favorites)
                    )
                }
            }
        }

        launch {
            setState { copy(isLoading = true) }
            comicsRepository.getComic(comicId).collect { resource ->
                val favoriteIds = favorites.map(Favorite::id)
                val comic = resource.getOrNull()?.let {
                    it.toPresentation(it.id in favoriteIds)
                }
                setState { copy(isLoading = false, comic = comic) }
            }
        }
        loadCharacters()
    }

    fun loadCharacters(offset: Int = 0) {
        launch {
            setState { copy(isLoading = true) }
            charactersRepository.getComicCharacters(comicId, offset).collect { resource ->
                val characters = resource.getOrNull()
                    ?.mapCharactersWithFavorites(favorites)
                    .orEmpty()
                setState { copy(isLoading = false, characters = characters) }
            }
        }
    }

    fun characterClicked(character: CharacterPresentation) =
        dispatchAction(GoToCharacterDetailAction(character.character.id))

    fun favoriteComicClicked() {
        val item = getState().comic ?: return
        launch {
            val favorite = item.comic.toFavorite()
            if (item.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }

    fun favoriteCharacterClicked(character: CharacterPresentation) {
        launch {
            val favorite = character.character.toFavorite()
            if (character.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }
}