package com.ablanco.marvellab.features.characters.presentation.detail

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.features.characters.di.detail.CharacterId
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
    private val comicsRepository: ComicsRepository
) : BaseViewModelFactory<CharacterDetailViewModel>() {

    override fun create(): CharacterDetailViewModel =
        CharacterDetailViewModel(characterId, charactersRepository, comicsRepository)
}

data class CharacterDetailViewState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val comics: List<Comic> = emptyList()
) : ViewState

class CharacterDetailViewModel(
    private val characterId: String,
    private val charactersRepository: CharactersRepository,
    private val comicsRepository: ComicsRepository
) : LoaderViewModel<CharacterDetailViewState, CharacterDetailViewAction>() {

    override val initialViewState: CharacterDetailViewState = CharacterDetailViewState()

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            charactersRepository.getCharacter(characterId).collect { character ->
                setState { copy(isLoading = false, character = character.getOrNull()) }
            }
            loadComics()
        }
    }

    fun loadComics(offset: Int = 0) {
        launch {
            setState { copy(isLoading = true) }
            comicsRepository.getCharacterComics(characterId, offset).collect {
                setState { copy(isLoading = false, comics = it.getOrNull().orEmpty()) }
            }
        }
    }

    fun characterComicClicked(comic: Comic) = dispatchAction(GoToCharacterComicAction(comic.id))
}