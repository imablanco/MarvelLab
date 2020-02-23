package com.ablanco.marvellab.features.characters.presentation.detail

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.extensions.toFavorite
import com.ablanco.marvellab.core.domain.model.favorites.Favorite
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.domain.repository.FavoritesRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.features.characters.di.detail.CharacterId
import com.ablanco.marvellab.features.characters.presentation.common.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-30.
 * MarvelLab.
 */

@FragmentScope
class CharacterDetailViewModelFactory @Inject constructor(
    @CharacterId private val characterId: String,
    private val charactersRepository: CharactersRepository,
    private val comicsRepository: ComicsRepository,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModelFactory<CharacterDetailViewModel>() {

    override fun create(): CharacterDetailViewModel =
        CharacterDetailViewModel(
            characterId,
            charactersRepository,
            comicsRepository,
            favoritesRepository
        )
}

data class CharacterDetailViewState(
    val isLoading: Boolean = false,
    val character: CharacterPresentation? = null,
    val comics: List<ComicPresentation> = emptyList()
) : ViewState

class CharacterDetailViewModel(
    private val characterId: String,
    private val charactersRepository: CharactersRepository,
    private val comicsRepository: ComicsRepository,
    private val favoritesRepository: FavoritesRepository
) : LoaderViewModel<CharacterDetailViewState, CharacterDetailViewAction>() {

    override val initialViewState: CharacterDetailViewState = CharacterDetailViewState()

    private var favorites: List<Favorite> = emptyList()

    override fun load() {
        launch {
            favoritesRepository.getAllFavorites().collect { resource ->
                favorites = resource.getOrNull().orEmpty()
                val characterId = getState().character?.character?.id
                val favoriteIds = favorites.map(Favorite::id)
                setState {
                    copy(
                        character = character?.copy(isFavorite = characterId in favoriteIds),
                        comics = comics.mapComicPresentationsWithFavorites(favorites)
                    )
                }
            }
        }

        launch {
            setState { copy(isLoading = true) }
            charactersRepository.getCharacter(characterId).collect { resource ->
                val favoriteIds = favorites.map(Favorite::id)
                val character = resource.getOrNull()?.let {
                    it.toPresentation(it.id in favoriteIds)
                }
                setState { copy(isLoading = false, character = character) }
            }
        }
        loadComics()
    }

    fun loadComics(offset: Int = 0) {
        launch {
            setState { copy(isLoading = true) }
            comicsRepository.getCharacterComics(characterId, offset).collect { resource ->
                val comics = resource.getOrNull()
                    ?.mapComicsWithFavorites(favorites)
                    .orEmpty()
                setState { copy(isLoading = false, comics = comics) }
            }
        }
    }

    fun characterComicClicked(comic: ComicPresentation) =
        dispatchAction(GoToCharacterComicAction(comic.comic.id))

    fun favoriteCharacterClicked() {
        val item = getState().character ?: return
        launch {
            val favorite = item.character.toFavorite()
            if (item.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }

    fun favoriteComicClicked(comic: ComicPresentation) {
        launch {
            val favorite = comic.comic.toFavorite()
            if (comic.isFavorite) {
                favoritesRepository.removeFavorite(favorite)
            } else {
                favoritesRepository.addFavorite(favorite)
            }
        }
    }
}