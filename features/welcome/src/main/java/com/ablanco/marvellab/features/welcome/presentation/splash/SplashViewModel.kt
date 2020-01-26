package com.ablanco.marvellab.features.welcome.presentation.splash

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.domain.repository.AuthRepository
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.LoaderViewModel
import com.ablanco.marvellab.core.presentation.ViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-07.
 * MarvelLab.
 */
@ActivityScope
class SplashViewModelFactory @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModelFactory<SplashViewModel>() {

    override fun create(): SplashViewModel = SplashViewModel(authRepository)
}

object SplashViewState : ViewState

class SplashViewModel(private val authRepository: AuthRepository) :
    LoaderViewModel<SplashViewState, SplashViewAction>() {

    override val initialViewState: SplashViewState = SplashViewState

    override fun load() {
        launch {
            val isLogged = authRepository.isUserLogged().getOrNull() ?: false
            dispatchAction(IsUserLoggedAction(isLogged))
        }
    }
}