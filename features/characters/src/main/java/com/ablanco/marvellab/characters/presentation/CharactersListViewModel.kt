package com.ablanco.marvellab.characters.presentation

import com.ablanco.marvellab.core.di.FragmentScope
import com.ablanco.marvellab.core.domain.model.Character
import com.ablanco.marvellab.core.domain.repository.CharactersRepository
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
    private val charactersRepository: CharactersRepository
) : BaseViewModelFactory<CharactersListViewModel>() {
    override fun create(): CharactersListViewModel = CharactersListViewModel(charactersRepository)
}


data class CharactersListViewState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList()
) : ViewState

class CharactersListViewModel(private val charactersRepository: CharactersRepository) :
    LoaderViewModel<CharactersListViewState, CharactersListViewAction>() {

    override val initialViewState: CharactersListViewState = CharactersListViewState()

    private var searchJob by autoCancelableJob()

    override fun load() = searchCharacters()

    fun searchCharacters(search: String? = null, offset: Int = 0) {
        searchJob = launch {
            setState { copy(isLoading = true) }
            charactersRepository.searchCharacters(search, offset).collect {
                setState { copy(isLoading = false, characters = it) }
            }
        }
    }

    fun characterClicked(character: Character) = dispatchAction(GoToCharacterDetail(character.id))
}