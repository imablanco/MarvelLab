package com.ablanco.marvellab.features.welcome.presentation.welcome

import com.ablanco.marvellab.core.di.ActivityScope
import com.ablanco.marvellab.core.presentation.BaseViewModel
import com.ablanco.marvellab.core.presentation.BaseViewModelFactory
import com.ablanco.marvellab.core.presentation.ViewState
import javax.inject.Inject

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-23.
 * MarvelLab.
 */

@ActivityScope
class WelcomeViewModelFactory @Inject constructor() : BaseViewModelFactory<WelcomeViewModel>() {

    override fun create(): WelcomeViewModel = WelcomeViewModel()
}

object WelcomeViewState : ViewState

class WelcomeViewModel : BaseViewModel<WelcomeViewState, WelcomeViewAction>() {

    override val initialViewState: WelcomeViewState = WelcomeViewState

    fun loginClicked() = dispatchAction(LoginAction)

    fun signUpClicked() = dispatchAction(SignUpAction)
}