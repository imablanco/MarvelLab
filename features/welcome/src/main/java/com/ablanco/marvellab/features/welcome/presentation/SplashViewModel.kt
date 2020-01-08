package com.ablanco.marvellab.features.welcome.presentation

import com.ablanco.marvellab.core.domain.repository.CharactersRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewAction
import com.ablanco.marvellab.core.presentation.ViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */

class SplashViewModelFactory @Inject constructor(
    private val charactersRepository: CharactersRepository
) : BaseViewModelFactory<SplashViewModel>() {

    override fun create(): SplashViewModel = SplashViewModel(charactersRepository)
}

object SplashViewState : ViewState

object SplashViewActions : ViewAction

class SplashViewModel(private val charactersRepository: CharactersRepository) :
    LoaderViewModel<SplashViewState, SplashViewActions>() {

    override val initialViewState: SplashViewState = SplashViewState

    override fun load() {

    }
}