package com.ablanco.marvellab.features.comics.presentation.detail

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.model.Comic
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.domain.repository.ComicsRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import com.ablanco.marvellab.features.comics.di.detail.ComicId
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
    private val charactersRepository: CharactersRepository
) : BaseViewModelFactory<ComicDetailViewModel>() {

    override fun create(): ComicDetailViewModel =
        ComicDetailViewModel(comicId, comicsRepository, charactersRepository)
}

data class ComicDetailViewState(
    val isLoading: Boolean = false,
    val comic: Comic? = null,
    val characters: List<Character> = emptyList()
) : ViewState

class ComicDetailViewModel(
    private val comicId: String,
    private val comicsRepository: ComicsRepository,
    private val charactersRepository: CharactersRepository
) : LoaderViewModel<ComicDetailViewState, ComicDetailViewAction>() {

    override val initialViewState: ComicDetailViewState = ComicDetailViewState()

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            comicsRepository.getComic(comicId).collect {
                setState { copy(isLoading = false, comic = it.getOrNull()) }
            }
        }
        loadCharacters()
    }

    fun loadCharacters(offset: Int = 0) {
        launch {
            setState { copy(isLoading = true) }
            charactersRepository.getComicCharacters(comicId, offset).collect {
                setState { copy(isLoading = false, characters = it.getOrNull().orEmpty()) }
            }
        }
    }

    fun characterClicked(character: Character) =
        dispatchAction(GoToCharacterDetailAction(character.id))

}